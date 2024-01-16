SET client_encoding TO 'UTF8';

DROP SEQUENCE IF EXISTS xhb_address_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_blob_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_case_diary_fixture_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_case_listing_entry_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_case_on_list_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_case_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_cc_info_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_clob_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_config_prop_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_court_room_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_court_satellite_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_court_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_court_site_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_cpp_formatting_merge_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_cpp_formatting_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_cpp_list_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_cpp_staging_inbound_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_formatting_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_hearing_list_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_hearing_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_list_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_ref_court_reporter_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_ref_court_report_f_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_ref_court_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_ref_cracked_effective_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_ref_hearing_type_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_ref_judge_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_ref_justice_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_ref_legal_rep_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_ref_listing_data_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_ref_monitoring_cat_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_ref_solicitor_firm_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_ref_system_code_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_scheduled_hearing_def_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_scheduled_hearing_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_sched_hearing_attend_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_sh_judge_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_sh_justice_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_sh_leg_rep_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_sh_staff_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_sitting_on_list_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_sitting_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_sys_audit_seq CASCADE;
DROP SEQUENCE IF EXISTS hk3_run_id_seq CASCADE;
DROP SEQUENCE IF EXISTS hk_cpp_run_id_seq CASCADE;
DROP SEQUENCE IF EXISTS hk_run_id_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_pdda_message_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_ref_pdda_message_type_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_case_reference_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_configured_public_not_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_court_log_entry_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_court_log_event_desc_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_cr_live_display_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_defendant_on_case_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_defendant_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_definitive_pub_notice_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_display_document_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_display_location_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_display_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_display_type_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_public_notice_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_rotation_sets_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_rotation_set_dd_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_pdda_dl_notifier_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_display_store_seq CASCADE;
DROP SEQUENCE IF EXISTS xhb_xml_document_seq CASCADE;

CREATE SEQUENCE xhb_address_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_blob_seq INCREMENT 1 MINVALUE -2147483648 MAXVALUE 2147483647 START 1 CACHE 20 CYCLE;
CREATE SEQUENCE xhb_case_diary_fixture_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_case_listing_entry_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_case_on_list_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_case_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_cc_info_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_clob_seq INCREMENT 1 MINVALUE -2147483648 MAXVALUE 2147483647 START 1 CACHE 20 CYCLE;
CREATE SEQUENCE xhb_config_prop_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_court_room_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_court_satellite_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_court_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_court_site_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_cpp_formatting_merge_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_cpp_formatting_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_cpp_list_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_cpp_staging_inbound_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_formatting_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_hearing_list_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_hearing_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_list_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_ref_court_reporter_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_ref_court_report_f_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_ref_court_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_ref_cracked_effective_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_ref_hearing_type_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_ref_judge_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_ref_justice_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_ref_legal_rep_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_ref_listing_data_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_ref_monitoring_cat_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_ref_solicitor_firm_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_ref_system_code_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_scheduled_hearing_def_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_scheduled_hearing_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_sched_hearing_attend_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_sh_judge_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_sh_justice_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_sh_leg_rep_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_sh_staff_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_sitting_on_list_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_sitting_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_sys_audit_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE hk3_run_id_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE hk_cpp_run_id_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE hk_run_id_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_pdda_message_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_ref_pdda_message_type_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_case_reference_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_configured_public_not_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_court_log_entry_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_court_log_event_desc_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_cr_live_display_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_defendant_on_case_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_defendant_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_definitive_pub_notice_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 2;
CREATE SEQUENCE xhb_display_document_seq INCREMENT 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE xhb_display_location_seq INCREMENT 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE xhb_display_seq INCREMENT 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE xhb_display_type_seq INCREMENT 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE xhb_public_notice_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_rotation_sets_seq INCREMENT 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE xhb_rotation_set_dd_seq INCREMENT 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE xhb_pdda_dl_notifier_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_display_store_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;
CREATE SEQUENCE xhb_xml_document_seq INCREMENT 1 MINVALUE 1 NO MAXVALUE START 1;