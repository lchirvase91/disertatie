-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema disertatie_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema disertatie_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `disertatie_db` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `disertatie_db` ;

-- -----------------------------------------------------
-- Table `disertatie_db`.`userlog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `disertatie_db`.`userlog` (
  `userlog_id` INT NOT NULL AUTO_INCREMENT,
  `userlog_username` VARCHAR(30) NOT NULL,
  `userlog_password` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`userlog_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `disertatie_db`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `disertatie_db`.`client` (
  `client_id` INT NOT NULL AUTO_INCREMENT,
  `client_nume` VARCHAR(45) NOT NULL,
  `client_judet` VARCHAR(30) NOT NULL,
  `client_localitate` VARCHAR(30) NOT NULL,
  `client_adresa` VARCHAR(100) NOT NULL,
  `client_telefon` VARCHAR(10) NULL,
  `client_email` VARCHAR(45) NULL,
  PRIMARY KEY (`client_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `disertatie_db`.`comanda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `disertatie_db`.`comanda` (
  `comanda_id` INT NOT NULL AUTO_INCREMENT,
  `comanda_nr_colete` INT NOT NULL,
  `comanda_greutate` INT NOT NULL,
  `comanda_data_comanda` DATE NOT NULL,
  `comanda_observatii` VARCHAR(100) NULL,
  `comanda_data_expediere` DATETIME NULL,
  `comanda_data_preluare` DATETIME NULL,
  `comanda_exp_id` INT NOT NULL,
  `comanda_dest_id` INT NOT NULL,
  PRIMARY KEY (`comanda_id`),
  INDEX `fk_comanda_exp_idx` (`comanda_exp_id` ASC),
  INDEX `fk_comanda_dest_idx` (`comanda_dest_id` ASC),
  CONSTRAINT `fk_comanda_exp`
    FOREIGN KEY (`comanda_exp_id`)
    REFERENCES `disertatie_db`.`client` (`client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comanda_dest`
    FOREIGN KEY (`comanda_dest_id`)
    REFERENCES `disertatie_db`.`client` (`client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `disertatie_db`.`colet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `disertatie_db`.`colet` (
  `colet_id` INT NOT NULL AUTO_INCREMENT,
  `colet_awb` BIGINT NOT NULL,
  `colet_status` VARCHAR(30) NOT NULL,
  `colet_comanda_id` INT NOT NULL,
  PRIMARY KEY (`colet_id`),
  UNIQUE INDEX `colet_awb_UNIQUE` (`colet_awb` ASC),
  INDEX `fk_colet_comanda_idx` (`colet_comanda_id` ASC),
  CONSTRAINT `fk_colet_comanda`
    FOREIGN KEY (`colet_comanda_id`)
    REFERENCES `disertatie_db`.`comanda` (`comanda_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `disertatie_db`.`hub`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `disertatie_db`.`hub` (
  `hub_id` INT NOT NULL AUTO_INCREMENT,
  `hub_judet` VARCHAR(30) NOT NULL,
  `hub_localitate` VARCHAR(30) NOT NULL,
  `hub_adresa` VARCHAR(100) NOT NULL,
  `hub_telefon` VARCHAR(10) NULL,
  `hub_email` VARCHAR(45) NULL,
  PRIMARY KEY (`hub_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `disertatie_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `disertatie_db`.`user` (
  `user_id` INT NOT NULL,
  `user_nume` VARCHAR(30) NOT NULL,
  `user_prenume` VARCHAR(30) NOT NULL,
  `user_telefon` VARCHAR(10) NOT NULL,
  `user_statut` VARCHAR(20) NOT NULL,
  `user_userlog_id` INT NOT NULL,
  `user_hub_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_cnp_UNIQUE` (`user_telefon` ASC),
  INDEX `fk_user_userlog_idx` (`user_userlog_id` ASC),
  INDEX `fk_user_hub1_idx` (`user_hub_id` ASC),
  CONSTRAINT `fk_user_userlog`
    FOREIGN KEY (`user_userlog_id`)
    REFERENCES `disertatie_db`.`userlog` (`userlog_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_hub1`
    FOREIGN KEY (`user_hub_id`)
    REFERENCES `disertatie_db`.`hub` (`hub_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `disertatie_db`.`operare`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `disertatie_db`.`operare` (
  `operare_user_id` INT NOT NULL,
  `operare_colet_id` INT NOT NULL,
  `operare_data` DATETIME NOT NULL,
  INDEX `fk_user_has_colet_colet_idx` (`operare_colet_id` ASC),
  INDEX `fk_user_has_colet_user_idx` (`operare_user_id` ASC),
  CONSTRAINT `fk_user_has_colet_user`
    FOREIGN KEY (`operare_user_id`)
    REFERENCES `disertatie_db`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_colet_colet`
    FOREIGN KEY (`operare_colet_id`)
    REFERENCES `disertatie_db`.`colet` (`colet_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `disertatie_db`.`proxi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `disertatie_db`.`proxi` (
  `proxi_id` INT NOT NULL,
  `proxi_hub_id_plecare` INT NOT NULL,
  `proxi_hub_id_sosire` INT NOT NULL,
  `proxi_next` INT NOT NULL,
  PRIMARY KEY (`proxi_id`),
  INDEX `fk_hub_has_hub_hub2_idx` (`proxi_hub_id_sosire` ASC),
  INDEX `fk_hub_has_hub_hub1_idx` (`proxi_hub_id_plecare` ASC),
  CONSTRAINT `fk_hub_has_hub_hub1`
    FOREIGN KEY (`proxi_hub_id_plecare`)
    REFERENCES `disertatie_db`.`hub` (`hub_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_hub_has_hub_hub2`
    FOREIGN KEY (`proxi_hub_id_sosire`)
    REFERENCES `disertatie_db`.`hub` (`hub_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
