module "application_insights" {
  source   = "git@github.com:hmcts/terraform-module-application-insights?ref=main"
  location = azurerm_resource_group.pdda_resource_group.location
  env      = var.env
  product  = [var.product, "pdm"]


  resource_group_name = azurerm_resource_group.pdda_resource_group.name

  common_tags = var.common_tags
}

moved {
  from = azurerm_application_insights.appinsights
  to   = module.application_insights.azurerm_application_insights.this
}
resource "azurerm_key_vault_secret" "app_insights_connection_string" {
  name         = "app-insights-connection-string"
  value        = module.application_insights.connection_string
  key_vault_id = module.pdda_key_vault.key_vault_id
}

resource "azurerm_key_vault_secret" "azure_appinsights_key" {
  name         = "AppInsightsInstrumentationKey"
  value        = module.application_insights.instrumentation_key
  key_vault_id = module.pdda_key_vault.key_vault_id
}

resource "azurerm_key_vault_secret" "app_insights_connection_string_pdm" {
  name         = "app-insights-connection-string-pdm"
  value        = module.application_insights.connection_string
  key_vault_id = module.pdda_key_vault.key_vault_id
}

resource "azurerm_key_vault_secret" "azure_appinsights_key_pdm" {
  name         = "AppInsightsInstrumentationKey-pdm"
  value        = module.application_insights.instrumentation_key
  key_vault_id = module.pdda_key_vault.key_vault_id
}