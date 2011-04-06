DESCRIPTION = "Package contain Makefile and Rule.make used for building DVSDK components"
LICENSE = "TI"

COMPATIBLE_MACHINE = "(omap3evm|am37x-evm|dm37x-evm|dm365-evm|da850-omapl138-evm|dm368-evm|c6a816x-evm|dm816x-evm|c6a814x-evm|dm814x-evm|dm816x-custom|dm814x-custom)"

require ti-paths.inc

UBOOT_ENV_dm365-evm = "setup-uboot-env-dm365.sh"
UBOOT_ENV_dm368-evm = "setup-uboot-env-dm365.sh"
UBOOT_ENV_omapl138 = "setup-uboot-env-omapl138.sh"
UBOOT_ENV_dm37x-evm = "setup-uboot-env-dm3730.sh"
UBOOT_ENV_omap3evm = "setup-uboot-env-omap3530.sh"
UBOOT_ENV_ti816x = "setup-uboot-env-c6a816x.sh"
UBOOT_ENV_ti814x = "setup-uboot-env-c6a814x.sh"
UBOOT_ENV_am37x-evm = "setup-uboot-env-am37x.sh"
UBOOT_ENV_am3517-evm = "setup-uboot-env-am3517.sh"
UBOOT_ENV_am180x-evm = "setup-uboot-env-am18x.sh"
UBOOT_ENV_am181x-evm = "setup-uboot-env-am18x.sh"
UBOOT_ENV_am389x-evm = "setup-uboot-env-am389x.sh"

SRC_URI = "\
	file://setup.sh \
  	file://common.sh \
  	file://setup-host-check.sh \
  	file://setup-minicom.sh \
  	file://setup-package-install.sh \
  	file://setup-targetfs-nfs.sh \
  	file://setup-tftp.sh \
    file://${UBOOT_ENV} \
"

PR = "r28"

do_install () {
    install -m 0755 ${WORKDIR}/setup.sh ${D}/
	install -d ${D}/bin
    install -m 0755 ${WORKDIR}/common.sh ${D}/bin
    install -m 0755 ${WORKDIR}/setup-host-check.sh ${D}/bin
    install -m 0755 ${WORKDIR}/setup-minicom.sh ${D}/bin
    install -m 0755 ${WORKDIR}/setup-package-install.sh ${D}/bin
    install -m 0755 ${WORKDIR}/setup-targetfs-nfs.sh ${D}/bin
    install -m 0755 ${WORKDIR}/setup-tftp.sh ${D}/bin
    install -m 0755 ${WORKDIR}/${UBOOT_ENV} ${D}/bin/setup-uboot-env.sh
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

FILES_${PN} += "setup.sh"
