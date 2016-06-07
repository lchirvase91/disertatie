update comanda set comanda_asignare = null where comanda_id =1;
update colet set colet_status='preluat',colet_awb = null where colet_comanda_id =1;

update comanda set comanda_asignare = null where comanda_id =2;
update colet set colet_status='preluat',colet_awb = null where colet_comanda_id =2;


update comanda set comanda_asignare = 1 where comanda_id = 3;
update colet set colet_status='in curs de preluare',colet_awb = null where colet_comanda_id = 3;


select distinct comanda_id, comanda_nr_colete, comanda_data_comanda from client, comanda, colet where client_id = comanda_exp_id and comanda_id = colet_comanda_id  
and colet_awb is null and colet_status = 'in curs de preluare' and comanda_asignare in (select user_id from user where user_hub_id = (select user_hub_id from user where user_userlog_id = 1)) 
and client_judet = (select hub_judet from hub where hub_id = (select user_hub_id from user where user_userlog_id = 1)) order by comanda_data_comanda


update colet, comanda, client, operare, user, hub set colet_status = 'in curs de preluare', comanda_asignare = 1 where colet_comanda_id = comanda_id and comanda_dest_id = client_id and colet_id = operare_colet_id and operare_user_id = user_id and user_hub_id = hub_id and hub_judet = client_judet and comanda_id = 3

select distinct comanda_id, comanda_nr_colete, comanda_data_comanda from client, comanda, colet where client_id = comanda_exp_id and comanda_id = colet_comanda_id  and colet_awb is null and colet_status = 'nepreluat' and comanda_asignare is null and client_judet = (select hub_judet from hub where hub_id = (select user_hub_id from user where user_userlog_id = 6)) order by comanda_data_comanda


select colet_id, count(*) from colet where colet_comanda_id = 2;




select * from  colet, comanda, client, operare, user, hub where colet_comanda_id = comanda_id AND comanda_exp_id = client_id AND colet_id = operare_colet_id AND operare_user_id = user_id AND user_hub_id = hub_id AND user_statut = 'operator' AND colet_id = 6 AND colet_status = 'procesat';

select operare_user_id from operare where operare_data = (select MAX(operare_data) from operare WHERE operare_user_id = 3 and operare_colet_id = 10);

select MAX(operare_data) INTO 'SSS' FROM operare WHERE operare_user_id = 3 AND operare_colet_id = 11;

select operare_user_id from operare where operare_data = (select MAX(operare_data) from operare WHERE operare_user_id = 3 and operare_colet_id = 11);



select proxi_next from proxi, comanda, client exp, client dest, hub hexp, hub hdest where exp.client_id = comanda_exp_id and dest.client_id = comanda_dest_id and proxi_hub_id_plecare = hexp.hub_id and proxi_hub_id_sosire = hdest.hub_id and hexp.hub_judet = exp.client_judet and hdest.hub_judet = dest.client_judet and comanda_id = 2 and proxi_next <> hdest.hub_id;

select user_id from user where user_statut = 'operator' and user_hub_id in (select proxi_next from proxi, hub, client exp, client dest, comanda where proxi_hub_id_sosire = hub_id and hub_judet = dest.client_judet and exp.client_id = comanda_exp_id and dest.client_id = comanda_dest_id and comanda_id = 2 and proxi_next <> proxi_hub_id_sosire);

select proxi_next from proxi, hub, client exp, client dest, comanda where proxi_hub_id_sosire = hub_id and hub_judet = dest.client_judet and exp.client_id = comanda_exp_id and dest.client_id = comanda_dest_id and proxi_next <> proxi_hub_id_sosire and comanda_id = 1 ;









select proxi_next from proxi where proxi_hub_id_sosire = (select hub_id from hub where hub_judet = (select client_judet  from client, comanda where client_id = comanda_dest_id and comanda_id = )) and proxi_next <> proxi_hub_id_sosire;