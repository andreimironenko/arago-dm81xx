DESCRIPTION = "Builds eDMA module used by eDMA libraries for PRU sw example applications"
HOMEPAGE = "https://gforge.ti.com/gf/project/pru_sw/"
LICENSE = "GPLv2"
MACHINE_KERNEL_PR_append = "r1+svnr${SRCPV}"

COMPATIBLE_MACHINE = "omapl138|am180x-evm|am181x-evm"

PACKAGE_STRIP = "no"

SRC_URI = "svn://gforge.ti.com/svn/pru_sw/;module=trunk;proto=https;user=anonymous;pswd=''"

SRCREV = "33"
S = "${WORKDIR}/trunk/peripheral_lib/edma_driver/module"

inherit module

do_compile_prepend () {
        export CCTOOL_PREFIX="${TOOLCHAIN_PATH}/bin/${TARGET_PREFIX}"
}

do_install () {
        install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/pru
        install -m 0755 ${S}/edmautils.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/pru/
}

FILES_${PN} = "/lib/modules/${KERNEL_VERSION}/kernel/drivers/pru/edmautils.ko"
