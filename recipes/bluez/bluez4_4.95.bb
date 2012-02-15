require recipes/bluez/bluez4.inc

SRC_URI = "\
  ${KERNELORG_MIRROR}/pub/linux/bluetooth/bluez-${PV}.tar.gz \
  file://bluetooth.conf \
  file://bluez-4.95-certification.patch \
  file://bluez4-fix-synchronization-between-bluetoothd-and-dr.patch \
"

SRC_URI[md5sum] = "341294b2849a04a4afff5c96bfbf30b2"
SRC_URI[sha256sum] = "d6ea9de410fc2bcd2620d709c2202893b218e2e6a55c3c0ce6bebd27fa4120f6"

# path to bluetooth.conf residing in arago-oe-dev
FILESPATHBASE .= ":${OEBASE}/arago-oe-dev/recipes/bluez"

DEPENDS += "libsndfile1"

PR = "${INC_PR}.3"

# Not all distros have a recent enough udev
BTUDEV = " --disable-udevrules"
BTUDEV_angstrom = " --enable-udevrules"
BTUDEV_shr = " --enable-udevrules"

EXTRA_OECONF += "${BTUDEV}"
do_configure_append(){
	echo "#define LIBUDEV_I_KNOW_THE_API_IS_SUBJECT_TO_CHANGE" >> ${S}/config.h
}

FILES_${PN}-dbg += "\
  ${base_libdir}/udev/.debug \
  ${libdir}/*/.debug \
"