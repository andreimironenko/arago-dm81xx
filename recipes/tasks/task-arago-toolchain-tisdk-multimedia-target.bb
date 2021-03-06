DESCRIPTION = "Task to build and install header and libs in sdk"
PR = "r4"
LICENSE = "MIT"

inherit task

EZSDK_MULTIMEDIA_COMMON = "\
    gstreamer-dev \
    gst-plugins-base-dev \
    gst-plugins-good-dev \
    gst-plugins-ugly-dev \
    gst-plugins-bad-dev \
    ortp-dev \
"

EZSDK_MULTIMEDIA_dm816x-evm = "\
    ${EZSDK_MULTIMEDIA_COMMON} \
"

EZSDK_MULTIMEDIA_dm816x-custom = "\
    ${EZSDK_MULTIMEDIA_COMMON} \
"

EZSDK_MULTIMEDIA_ti814x = "\
    ${EZSDK_MULTIMEDIA_COMMON} \
"

EZSDK_MULTIMEDIA_ti811x = "\
    ${EZSDK_MULTIMEDIA_COMMON} \
"

RDEPENDS_${PN} = "\
    ${EZSDK_MULTIMEDIA} \
#    gst-plugins-good-dev \
#    gst-plugins-ugly-dev \
#    gst-plugins-bad-dev \
    "
