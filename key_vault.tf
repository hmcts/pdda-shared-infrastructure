module "pdda_key_vault" {
  source = "git@github.com:hmcts/cnp-module-key-vault?ref=DTSPO-31965/remove-jenkins-ptl-access"

  # SDS only: allows dev pipelines to read from the STG vault
  grant_dev_jenkins_access = var.env == "stg"

  name                    = "${var.product}-${var.env}"
  product                 = var.product
  env                     = var.env
  object_id               = var.jenkins_AAD_objectId
  resource_group_name     = azurerm_resource_group.pdda_resource_group.name
  product_group_name      = "DTS SDS PDDA"
  create_managed_identity = true
  developers_group        = var.developers_group
  jenkins_object_id       = data.azurerm_user_assigned_identity.jenkins.principal_id

  common_tags = var.common_tags
}
