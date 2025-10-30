interface ProvisioningRunner {
  fun makePlan(firmTofu: UnpackedFirmTofu): Plan

  fun executePlan(firmTofu: UnpackedFirmTofu, plan: Plan)
}
