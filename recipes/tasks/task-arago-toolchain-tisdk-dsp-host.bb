DESCRIPTION = "Task to build and install source (or development) packages on host"
PR = "r14"
LICENSE="MIT"

inherit task
PACKAGE_ARCH = "${MACHINE_ARCH}"

DSP_DVSDK_COMMON = " \
    ti-cgt6x-src \
    ti-dspbios-src \
    ti-biosutils-src \
    ti-dsplink-src \
    ti-edma3lld-src \
"

DSP_SOURCETREE_dm6446 = "\
    ${DSP_DVSDK_COMMON} \
    "

DSP_SOURCETREE_dm6467 = "\
    ${DSP_DVSDK_COMMON} \
    "

DSP_SOURCETREE_omapl137 = "\
    ${DSP_DVSDK_COMMON} \
    ti-biospsp-src \
    ti-audio-soc-example-src \
    "

DSP_SOURCETREE_omapl138 = "\
    ${DSP_DVSDK_COMMON} \
    ti-biospsp-src \
    ti-audio-soc-example-src \
    ti-c6accel-src \
    ti-dsp-benchmark-src \
    ti-framework-components-src \
    ti-codec-engine-src \
    ti-xdais-src \
    ti-c6run-src \
    ti-dsp-benchmark-src \
    "

DSP_SOURCETREE_omap3 = "\
    ${DSP_DVSDK_COMMON} \
    ti-c6accel-src \
    ti-framework-components-src \
    ti-codec-engine-src \
    ti-xdais-src \
    ti-c6run-src \
    "

DSP_SOURCETREE = " "

RRECOMMENDS_${PN} = "\
    ${DSP_SOURCETREE} \
    "

