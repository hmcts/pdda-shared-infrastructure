SET client_encoding TO 'UTF8';

ALTER TABLE xhb_case DROP CONSTRAINT case_ccc_trans_fr_ref_ct_id_fk;
ALTER TABLE xhb_case DROP CONSTRAINT case_ccc_trans_to_ref_ct_id_fk;
ALTER TABLE xhb_case DROP CONSTRAINT case_court_id_fk;
ALTER TABLE xhb_case DROP CONSTRAINT case_cracked_ineffective_id_fk;
ALTER TABLE xhb_case DROP CONSTRAINT case_default_hearing_type_fk;
ALTER TABLE xhb_case DROP CONSTRAINT case_ref_court_id_fk;
ALTER TABLE xhb_case DROP CONSTRAINT xhb_court_receiving_code_fk;
ALTER TABLE xhb_case DROP CONSTRAINT xhb_magcourt_hearing_fk;
ALTER TABLE xhb_case DROP CONSTRAINT xhb_monitoringcategory_id_fk;
ALTER TABLE xhb_case DROP CONSTRAINT xhb_policeforce_code_fk;
ALTER TABLE xhb_case DROP CONSTRAINT xhb_tickettype_code_fk;
ALTER TABLE xhb_case_diary_fixture DROP CONSTRAINT cdf_case_listing_entry_id_fk;
ALTER TABLE xhb_case_diary_fixture DROP CONSTRAINT cdf_free_text_note_class_id_fk;
ALTER TABLE xhb_case_diary_fixture DROP CONSTRAINT cdf_hearing_type_id_fk;
ALTER TABLE xhb_case_diary_fixture DROP CONSTRAINT cdf_listnotepredefinedid_fk;
ALTER TABLE xhb_case_diary_fixture DROP CONSTRAINT cdf_pre_def_note_class_id_fk;
ALTER TABLE xhb_case_diary_fixture DROP CONSTRAINT cdf_vacation_predef_rson_id_fk;
ALTER TABLE xhb_case_diary_fixture DROP CONSTRAINT xhb_case_diary_fixture_cs_id;
ALTER TABLE xhb_case_listing_entry DROP CONSTRAINT case_listing_court_site_id_fk;
ALTER TABLE xhb_case_listing_entry DROP CONSTRAINT case_listing_judge_id_fk;
ALTER TABLE xhb_case_listing_entry DROP CONSTRAINT case_listing_judge_type_id_fk;
ALTER TABLE xhb_case_listing_entry DROP CONSTRAINT xhb_case_list_entry_case_id_fk;
ALTER TABLE xhb_case_listing_entry DROP CONSTRAINT xhb_case_list_entry_crt_id_fk;
ALTER TABLE xhb_case_on_list DROP CONSTRAINT xhb_case_on_list_case_id_fk;
ALTER TABLE xhb_case_on_list DROP CONSTRAINT xhb_case_on_list_cdf_fk;
ALTER TABLE xhb_case_on_list DROP CONSTRAINT xhb_case_on_list_col_fk;
ALTER TABLE xhb_case_on_list DROP CONSTRAINT xhb_case_on_list_cr_id_fk;
ALTER TABLE xhb_case_on_list DROP CONSTRAINT xhb_case_on_list_cr_iid_id_fk;
ALTER TABLE xhb_case_on_list DROP CONSTRAINT xhb_case_on_list_cs_id_fk;
ALTER TABLE xhb_case_on_list DROP CONSTRAINT xhb_case_on_list_list_id_fk;
ALTER TABLE xhb_case_on_list DROP CONSTRAINT xhb_case_on_list_lnpd_fk;
ALTER TABLE xhb_case_on_list DROP CONSTRAINT xhb_case_on_list_rht_id_fk;
ALTER TABLE xhb_case_on_list DROP CONSTRAINT xhb_case_on_list_sol_fk;
ALTER TABLE xhb_case_on_list DROP CONSTRAINT xhb_case_on_list_tm_id_fk;
ALTER TABLE xhb_case_on_list DROP CONSTRAINT xhb_case_on_list_vpdri_fk;
ALTER TABLE xhb_court DROP CONSTRAINT court_address_id_fk;
ALTER TABLE xhb_court DROP CONSTRAINT xhb_police_force_code_fk;
ALTER TABLE xhb_court_room DROP CONSTRAINT court_room_court_site_id_fk;
ALTER TABLE xhb_court_satellite DROP CONSTRAINT sys_c0012884;
ALTER TABLE xhb_court_site DROP CONSTRAINT court_site_address_id_fk;
ALTER TABLE xhb_court_site DROP CONSTRAINT court_site_court_id_fk;
ALTER TABLE xhb_cpp_formatting DROP CONSTRAINT cpp_clob_id_fk;
ALTER TABLE xhb_cpp_formatting DROP CONSTRAINT cpp_court_id_fk;
ALTER TABLE xhb_cpp_formatting DROP CONSTRAINT cpp_staging_id_fk;
ALTER TABLE xhb_cpp_formatting_merge DROP CONSTRAINT cpp_merge_clob_id_fk;
ALTER TABLE xhb_cpp_formatting_merge DROP CONSTRAINT cpp_merge_court_id_fk;
ALTER TABLE xhb_cpp_formatting_merge DROP CONSTRAINT cpp_merge_cpp_formatting_id_fk;
ALTER TABLE xhb_cpp_formatting_merge DROP CONSTRAINT cpp_merge_formatting_id_fk;
ALTER TABLE xhb_cpp_list DROP CONSTRAINT list_clob_id_fk;
ALTER TABLE xhb_cpp_list DROP CONSTRAINT merged_clob_id_fk;
ALTER TABLE xhb_cpp_staging_inbound DROP CONSTRAINT cpp_staging_clob_id_fk;
ALTER TABLE xhb_formatting DROP CONSTRAINT formatting_court_id_fk;
ALTER TABLE xhb_formatting DROP CONSTRAINT xhb_format_format_blob_id_fk;
ALTER TABLE xhb_hearing DROP CONSTRAINT hearing_case_id_fk;
ALTER TABLE xhb_hearing DROP CONSTRAINT hearing_court_id_fk;
ALTER TABLE xhb_hearing DROP CONSTRAINT hearing_ref_hearing_type_id_fk;
ALTER TABLE xhb_hearing_list DROP CONSTRAINT hearing_list_court_id_fk;
ALTER TABLE xhb_list DROP CONSTRAINT xhb_list_court_id_fk;
ALTER TABLE xhb_list DROP CONSTRAINT xhb_list_list_type_id_fk;
ALTER TABLE xhb_list DROP CONSTRAINT xhb_list_parent_fk;
ALTER TABLE xhb_ref_court DROP CONSTRAINT ref_court_address_id_fk;
ALTER TABLE xhb_ref_court DROP CONSTRAINT ref_court_court_id_fk;
ALTER TABLE xhb_ref_court_reporter DROP CONSTRAINT ref_ct_rep_court_id_fk;
ALTER TABLE xhb_ref_court_reporter DROP CONSTRAINT ref_ct_rep_ref_ct_rep_f_id_fk;
ALTER TABLE xhb_ref_court_reporter_firm DROP CONSTRAINT ref_ct_rep_firm_address_id_fk;
ALTER TABLE xhb_ref_court_reporter_firm DROP CONSTRAINT ref_ct_rep_firm_court_id_fk;
ALTER TABLE xhb_ref_hearing_type DROP CONSTRAINT ref_hearing_type_court_id_fk;
ALTER TABLE xhb_ref_judge DROP CONSTRAINT ref_judge_court_id_fk;
ALTER TABLE xhb_ref_justice DROP CONSTRAINT ref_justice_court_id_fk;
ALTER TABLE xhb_ref_legal_representative DROP CONSTRAINT ref_legal_rep_court_id_fk;
ALTER TABLE xhb_ref_solicitor_firm DROP CONSTRAINT ref_sol_firm_address_id_fk;
ALTER TABLE xhb_ref_solicitor_firm DROP CONSTRAINT ref_sol_firm_court_id_fk;
ALTER TABLE xhb_ref_system_code DROP CONSTRAINT ref_system_code_court_id_fk;
ALTER TABLE xhb_scheduled_hearing DROP CONSTRAINT sched_hear_hearing_id_fk;
ALTER TABLE xhb_scheduled_hearing DROP CONSTRAINT sched_hear_moved_f_ct_rm_id_fk;
ALTER TABLE xhb_scheduled_hearing DROP CONSTRAINT sched_hear_sitting_id_fk;
ALTER TABLE xhb_scheduled_hearing DROP CONSTRAINT xhb_scheduled_hearing_rce_fk;
ALTER TABLE xhb_sched_hearing_attendee DROP CONSTRAINT sched_h_att_ref_ct_rep_id_fk;
ALTER TABLE xhb_sched_hearing_attendee DROP CONSTRAINT sched_h_att_ref_judge_id_fk;
ALTER TABLE xhb_sched_hearing_attendee DROP CONSTRAINT sched_h_att_ref_justice_id_fk;
ALTER TABLE xhb_sched_hearing_attendee DROP CONSTRAINT sched_h_att_sched_hear_id_fk;
ALTER TABLE xhb_sched_hearing_attendee DROP CONSTRAINT sched_h_att_sh_justice_id_fk;
ALTER TABLE xhb_sched_hearing_attendee DROP CONSTRAINT sched_h_att_sh_staff_id_fk;
ALTER TABLE xhb_sched_hearing_defendant DROP CONSTRAINT sched_h_def_sched_hear_id_fk;
ALTER TABLE xhb_sh_judge DROP CONSTRAINT sh_judge_ref_judge_id_fk;
ALTER TABLE xhb_sh_judge DROP CONSTRAINT sh_judge_sh_attendee_id_fk;
ALTER TABLE xhb_sh_justice DROP CONSTRAINT sh_justice_hearing_id_fk;
ALTER TABLE xhb_sh_leg_rep DROP CONSTRAINT sh_leg_rep_cc_info_id_fk;
ALTER TABLE xhb_sh_leg_rep DROP CONSTRAINT sh_leg_rep_ref_def_cat_id_fk;
ALTER TABLE xhb_sh_leg_rep DROP CONSTRAINT sh_leg_rep_ref_legal_rep_id_fk;
ALTER TABLE xhb_sh_leg_rep DROP CONSTRAINT sh_leg_rep_ref_sol_firm_id_fk;
ALTER TABLE xhb_sh_leg_rep DROP CONSTRAINT sh_leg_rep_sched_hearing_id_fk;
ALTER TABLE xhb_sh_leg_rep DROP CONSTRAINT sh_leg_rep_sched_h_def_id_fk;
ALTER TABLE xhb_sh_leg_rep DROP CONSTRAINT sh_leg_rep_sub_ref_leg_rep_fk;
ALTER TABLE xhb_sitting DROP CONSTRAINT sitting_court_room_id_fk;
ALTER TABLE xhb_sitting DROP CONSTRAINT sitting_court_site_id_fk;
ALTER TABLE xhb_sitting DROP CONSTRAINT sitting_list_id_fk;
ALTER TABLE xhb_sitting DROP CONSTRAINT sitting_ref_judge_id_fk;
ALTER TABLE xhb_sitting_on_list DROP CONSTRAINT sitt_on_list_court_room_id_fk;
ALTER TABLE xhb_sitting_on_list DROP CONSTRAINT sitt_on_list_court_site_id_fk;
ALTER TABLE xhb_sitting_on_list DROP CONSTRAINT sol_free_text_note_class_id_fk;
ALTER TABLE xhb_sitting_on_list DROP CONSTRAINT sol_judge_ref_id_fk;
ALTER TABLE xhb_sitting_on_list DROP CONSTRAINT sol_listnotepredefinedid_fk;
ALTER TABLE xhb_sitting_on_list DROP CONSTRAINT sol_list_id_fk;
ALTER TABLE xhb_sitting_on_list DROP CONSTRAINT sol_pre_def_note_class_id_fk;
ALTER TABLE xhb_sitting_on_list DROP CONSTRAINT sol_time_marking_id_fk;
ALTER TABLE xhb_pdda_message DROP CONSTRAINT pddamsg_court_fk;
ALTER TABLE xhb_pdda_message DROP CONSTRAINT pddamsg_court_room_fk;
ALTER TABLE xhb_pdda_message DROP CONSTRAINT pddamsg_message_type_fk;
ALTER TABLE xhb_pdda_message DROP CONSTRAINT pddamsg_clob_fk;
ALTER TABLE xhb_pdda_message DROP CONSTRAINT pddamsg_cppstaging_fk;
ALTER TABLE xhb_case_reference DROP CONSTRAINT case_reference_case_id_fk;
ALTER TABLE xhb_configured_public_notice DROP CONSTRAINT con_pub_not_court_room_id_fk;
ALTER TABLE xhb_configured_public_notice DROP CONSTRAINT con_pub_not_public_not_id_fk;
ALTER TABLE xhb_court_log_entry DROP CONSTRAINT ct_log_entry_case_id_fk;
ALTER TABLE xhb_court_log_entry DROP CONSTRAINT ct_log_entry_def_on_cas_id_fk;
ALTER TABLE xhb_court_log_entry DROP CONSTRAINT ct_log_entry_event_desc_id_fk;
ALTER TABLE xhb_court_log_entry DROP CONSTRAINT ct_log_entry_sched_hear_id_fk;
ALTER TABLE xhb_cr_live_display DROP CONSTRAINT cr_live_disp_court_room_id_fk;
ALTER TABLE xhb_cr_live_display DROP CONSTRAINT cr_live_disp_sched_hear_id_fk;
ALTER TABLE xhb_defendant DROP CONSTRAINT defendant_address_id_fk;
ALTER TABLE xhb_defendant DROP CONSTRAINT defendant_court_id_fk;
ALTER TABLE xhb_defendant_on_case DROP CONSTRAINT def_on_case_case_id_fk;
ALTER TABLE xhb_defendant_on_case DROP CONSTRAINT def_on_case_col_mag_ct_id_fk;
ALTER TABLE xhb_defendant_on_case DROP CONSTRAINT def_on_case_defendant_id_fk;
ALTER TABLE xhb_display DROP CONSTRAINT display_display_location_id_fk;
ALTER TABLE xhb_display DROP CONSTRAINT display_display_type_id_fk;
ALTER TABLE xhb_display DROP CONSTRAINT display_rotation_set_id_fk;
ALTER TABLE xhb_display_court_room DROP CONSTRAINT display_court_rm_ct_rm_id_fk;
ALTER TABLE xhb_display_court_room DROP CONSTRAINT display_court_rm_display_id_fk;
ALTER TABLE xhb_display_location DROP CONSTRAINT display_loc_court_site_id_fk;
ALTER TABLE xhb_public_notice DROP CONSTRAINT public_not_court_id_fk;
ALTER TABLE xhb_public_notice DROP CONSTRAINT public_not_definitive_pn_id_fk;
ALTER TABLE xhb_rotation_sets DROP CONSTRAINT rotation_sets_court_id_fk;
ALTER TABLE xhb_rotation_set_dd DROP CONSTRAINT rot_set_dd_display_doc_id_fk;
ALTER TABLE xhb_rotation_set_dd DROP CONSTRAINT rot_set_dd_rotation_set_id_fk;
ALTER TABLE xhb_pdda_dl_notifier DROP CONSTRAINT pdda_dl_notifier_court_id_fk;
ALTER TABLE xhb_xml_document DROP CONSTRAINT xhb_xml_document_clob_id_fk;