
COMPATIBLE_MACHINE = "(dm365-evm|dm37x-evm|omap3evm)"

PV = "4_00_00_16"
SRCREV         = "9792"
DEMOSBRANCH    = "tags/TAG_${PV}"

RDEPENDS_append = " ti-dvsdk-demos-qtinterface "
require ti-dvsdk-demos.inc
