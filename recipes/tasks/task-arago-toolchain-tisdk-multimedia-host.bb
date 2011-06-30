DESCRIPTION = "Task to build and install multimedia source (or development header) packages on host"
PR = "r25"
LICENSE = "MIT"

inherit task
PACKAGE_ARCH = "${MACHINE_ARCH}"

MULTIMEDIA_SOURCETREE = ""

MULTIMEDIA_DVSDK_COMMON = " \
    ti-framework-components-src \
    ti-codec-engine-src \
    ti-linuxutils-src \
    ti-xdctools-src \
    ti-dmai-src \
    ti-xdais-src \
    gstreamer-ti-src \
 "

MULTIMEDIA_SOURCETREE_omap3 = " \
    ${MULTIMEDIA_DVSDK_COMMON} \
    ti-codecs-omap3530-src \
    ti-local-power-manager-src \
    ti-dvtb-src \
    ti-dvsdk-demos-src\
    "

MULTIMEDIA_SOURCETREE_dm355 = " \
    ${MULTIMEDIA_DVSDK_COMMON} \
    ti-codecs-dm355-src \
    "

MULTIMEDIA_SOURCETREE_dm365 = " \
    ${MULTIMEDIA_DVSDK_COMMON} \
    ti-codecs-dm365-src \
    ti-dm365mm-module-src \
    ti-dvtb-src \
    ti-dvsdk-demos-src\
    "

MULTIMEDIA_SOURCETREE_dm6446 = " \
    ${MULTIMEDIA_DVSDK_COMMON} \
    ti-codecs-dm6446-src \
    ti-local-power-manager-src \
    "

MULTIMEDIA_SOURCETREE_dm6467 = " \
    ${MULTIMEDIA_DVSDK_COMMON} \
    ti-codecs-dm6467-src \
    "

MULTIMEDIA_SOURCETREE_omapl137 = " \
    ${MULTIMEDIA_DVSDK_COMMON} \
    ti-codecs-omapl137-src \
    "

MULTIMEDIA_SOURCETREE_omapl138 = " \
    ${MULTIMEDIA_DVSDK_COMMON} \
    ti-codecs-omapl138-src \
    "

# Do not pull DSP sources into AMSDK builds
MULTIMEDIA_SOURCETREE_am37x-evm = ""
MULTIMEDIA_SOURCETREE_beagleboard = ""
MULTIMEDIA_SOURCETREE_am180x-evm = ""
MULTIMEDIA_SOURCETREE_am181x-evm = ""
MULTIMEDIA_SOURCETREE_am389x-evm = ""
MULTIMEDIA_SOURCETREE_am3517-evm = ""
MULTIMEDIA_SOURCETREE_am387x-evm = ""
MULTIMEDIA_SOURCETREE_am45x-evm = ""

RRECOMMENDS_${PN} = "\
    ${MULTIMEDIA_SOURCETREE} \
    "

