module "postgresql" {

  providers = {
    azurerm.postgres_network = azurerm.postgres_network
  }

  source = "git@github.com:hmcts/terraform-module-postgresql-flexible?ref=master"
  env    = var.env

  product       = var.product
  component     = var.component
  business_area = var.business_area

  # The original subnet is full, this is required to use the new subnet for new databases
  subnet_suffix = "expanded"

  pgsql_databases = [
    {
      name : "pdda"
    }
  ]

  pgsql_sku     = "GP_Standard_D2ds_v4"
  pgsql_version = "16"

  # The ID of the principal to be granted admin access to the database server.
  # On Jenkins it will be injected for you automatically as jenkins_AAD_objectId.
  # Otherwise change the below:
  admin_user_object_id = var.jenkins_AAD_objectId

  common_tags = var.common_tags
}

resource "azurerm_key_vault_secret" "postgres-user" {
  name         = "public-display-data-aggregator-POSTGRES-USER"
  value        = module.postgresql.username
  key_vault_id = module.pdda_key_vault.key_vault_id
}

resource "azurerm_key_vault_secret" "postgres-password" {
  name         = "public-display-data-aggregator-POSTGRES-PASS"
  value        = module.postgresql.password
  key_vault_id = module.pdda_key_vault.key_vault_id
}

resource "azurerm_key_vault_secret" "postgres-host" {
  name         = "public-display-data-aggregator-POSTGRES-HOST"
  value        = module.postgresql.fqdn
  key_vault_id = module.pdda_key_vault.key_vault_id
}

resource "azurerm_key_vault_secret" "postgres-port" {
  name         = "public-display-data-aggregator-POSTGRES-PORT"
  value        = "5432"
  key_vault_id = module.pdda_key_vault.key_vault_id
}

resource "azurerm_key_vault_secret" "postgres-database" {
  name         = "public-display-data-aggregator-POSTGRES-DATABASE"
  value        = "pdda"
  key_vault_id = module.pdda_key_vault.key_vault_id
}

resource "azurerm_key_vault_secret" "postgres-schema" {
  name         = "public-display-data-aggregator-POSTGRES-SCHEMA"
  value        = "pdda"
  key_vault_id = module.pdda_key_vault.key_vault_id
}