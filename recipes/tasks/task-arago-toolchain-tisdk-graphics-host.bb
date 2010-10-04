DESCRIPTION = "Task to install graphics application sources on host"
PR = "r5"
LICENSE = "MIT"

inherit task
PACKAGE_ARCH = "${MACHINE_ARCH}"

GRAPHICS_APPS = ""

GRAPHICS_APPS_omap3 = " \
    matrix-gui-e-src \
    "
   
GRAPHICS_APPS_am37x-evm = " \
    matrix-gui-e-src \
    "

GRAPHICS_APPS_ti816x = " \
    matrix-gui-e-src \
    "

GRAPHICS_APPS_dm37x-evm = " \
    matrix-gui-e-src \
    "

GRAPHICS_APPS_omap3evm = " \
    matrix-gui-e-src \
    "

GRAPHICS_APPS_omapl138 = " \
    matrix-gui-e-src \
    "

GRAPHICS_APPS_dm365 = " \
    matrix-gui-e-src \
    "

RDEPENDS_${PN} = "\
    ${GRAPHICS_APPS} \
    "
