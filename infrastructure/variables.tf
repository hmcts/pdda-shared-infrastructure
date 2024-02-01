variable "product" {}

variable "component" {}

variable "location" {
  default = "UK South"
}

variable "env" {}

variable "subscription" {}

variable "aks_subscription_id" {} # provided by the Jenkins library, ADO users will need to specify this

variable "common_tags" {
  type = map(string)
}

# Private DNS zone configuration (for postgres)
variable "dns_resource_group" {
  default = "core-infra-intsvc-rg"
}

variable "private_dns_zone" {
  default = "private.postgres.database.azure.com"
}


