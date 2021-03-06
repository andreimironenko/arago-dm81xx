require ti-media-controller-loader.inc

PV = "3_00_00_04"

SRC_URI = "https://gforge.ti.com/gf/download/frsrelease/884/5798/media-controller-utils_${PV}.tar.gz \
           file://load-hd-firmware.sh \
           file://change_display.sh \
"

SRC_URI[md5sum] = "ee94fbcfb1199f8a83b1a292c8d09847"
SRC_URI[sha256sum] = "ab0101a8e61fa2cae9a55ed7816a0e264b6c7e0147f6b96675e9e90e6428960a"

S = "${WORKDIR}/media-controller-utils_${PV}/src/linux/${PLATFORM}"

do_copyimage() {
    cp -f ${WORKDIR}/change_display.sh ${WORKDIR}/media-controller-utils_${PV}/src/linux/ti811x-evm/change_display.sh
    cp -f ${WORKDIR}/load-hd-firmware.sh ${WORKDIR}/media-controller-utils_${PV}/src/linux/ti811x-evm/load-hd-firmware.sh
}
addtask copyimage after do_unpack before do_patch
