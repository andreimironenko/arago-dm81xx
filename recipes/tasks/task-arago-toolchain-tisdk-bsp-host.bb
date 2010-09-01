DESCRIPTION = "Task to build and install Board Support Package sources (or development header) packages on host"
PR = "r32"
LICENSE = "MIT"
ALLOW_EMPTY = "1"

inherit task
PACKAGE_ARCH = "${MACHINE_ARCH}"

BSP_COMPONENTS_COMMON = "\
    ti-tisdk-licenses \
    u-boot-src \
    "

BSP_COMPONENTS_dm365 = "\
    ti-tisdk-relnotes \
    ti-tisdk-setup \
    ti-docs-sdg \
    ti-docs-quickstart \
    ti-docs-psp \
    ti-board-utilities \
    ti-linux-driver-examples-src \
    linux-davinci-staging-src \
    "

BSP_COMPONENTS_dm355 = "\
    ti-linux-driver-examples-src \
    "

BSP_COMPONENTS_dm6446 = "\
    ti-linux-driver-examples-src \
    "

BSP_COMPONENTS_dm6467 = "\
    ti-linux-driver-examples-src \
    "

BSP_COMPONENTS_da850-omapl138-evm = "\
    ti-tisdk-setup \
    ti-tisdk-mksdboot \
    ti-tisdk-relnotes \
    ti-docs-sdg \
    ti-docs-quickstart \
    ti-docs-psp \
    linux-omapl1-src \
    ti-board-utilities \
    ti-linux-driver-examples-omapl-src \
    "

BSP_COMPONENTS_append_omap3 = "\
    linux-omap3-src \
    x-load-src \
    "

# Add components to omap3 components
BSP_COMPONENTS_append_omap3evm = "\
    ti-tisdk-setup \
    ti-tisdk-relnotes \
    ti-docs-sdg \
    ti-docs-qsg-hardcopy \
    ti-docs-hug-hardcopy \
    ti-docs-psp \
    ti-tisdk-mksdboot \
    ti-linux-driver-examples-omap3-src \
    "

# Add components to omap3 components
BSP_COMPONENTS_append_am37x-evm = "\
    ti-tisdk-setup \
    flash-utility \
    "

# Add components to omap3 components
BSP_COMPONENTS_append_dm37x-evm = "\
    ti-tisdk-setup \
    ti-tisdk-relnotes \
    ti-docs-sdg \
    ti-docs-qsg-hardcopy \
    ti-docs-hug-hardcopy \
    ti-docs-psp \
    ti-tisdk-mksdboot \
    ti-linux-driver-examples-omap3-src \
    "

RRECOMMENDS_${PN} = "\
    ${BSP_COMPONENTS_COMMON} \
    ${BSP_COMPONENTS} \
    "