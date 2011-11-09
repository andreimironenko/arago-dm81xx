DESCRIPTION = "Task to install Board Support Package binaries on ${MACHINE}"
PR = "r26"
LICENSE = "MIT"
ALLOW_EMPTY = "1"

inherit task
PACKAGE_ARCH = "${MACHINE_ARCH}"

BSP_COMPONENTS = ""

BSP_COMPONENTS_dm355 = "\
    ti-linux-driver-examples \
    "

BSP_COMPONENTS_dm37x-evm = "\
    x-load \
    "

BSP_COMPONENTS_dm365 = "\
    ti-linux-driver-examples \
    "

BSP_COMPONENTS_dm6446 = "\
    ti-linux-driver-examples \
    "

BSP_COMPONENTS_dm6467 = "\
    ti-linux-driver-examples \
    "

BSP_COMPONENTS_append_am37x-evm = "\
    av-examples \
    "

BSP_COMPONENTS_append_am3517-evm = "\
    av-examples \
    "

BSP_COMPONENTS_append_omap3 = "\
    u-boot-spl \
    "

BSP_COMPONENTS_ti33x = "\
    u-boot-spl \
    gadget-init \
    ti-oobe-script \
    "

BSP_COMPONENTS_ti816x = "\
    u-boot-min-sd \
    "

BLUETOOTH_STACK = "\
    bluez4 \
    bluez4-agent \
    libasound-module-bluez \
    bluez-hcidump \
    openobex \
    openobex-apps \
    obexftp \
    ussp-push \
    "

BLUETOOTH_SUPPORT = ""
BLUETOOTH_SUPPORT_omap3 = "${BLUETOOTH_STACK}"
BLUETOOTH_SUPPORT_omapl138 = "${BLUETOOTH_STACK}"
BLUETOOTH_SUPPORT_ti816x = "${BLUETOOTH_STACK}"
BLUETOOTH_SUPPORT_ti814x = "${BLUETOOTH_STACK}"
BLUETOOTH_SUPPORT_omap4 = "${BLUETOOTH_STACK}"
BLUETOOTH_SUPPORT_ti33x = "${BLUETOOTH_STACK}"

RDEPENDS_${PN} = "\
    ${BSP_COMPONENTS} \
    ${BLUETOOTH_SUPPORT} \
    "

RRECOMMENDS_${PN} = "\
    kernel-modules \
    kernel-image \
    kernel-vmlinux \
    u-boot \
    "
