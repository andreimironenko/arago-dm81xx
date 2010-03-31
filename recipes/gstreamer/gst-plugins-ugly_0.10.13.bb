require gst-plugins.inc

PR = "${INC_PR}.1"

DEPENDS += "gst-plugins-base libmad "

SRC_URI += "\
  file://gstmad_16bit.patch;patch=1 \
  file://gstsid_autofoo_HACK.patch;patch=1 \
"
EXTRA_OECONF += "ac_cv_openssldir=no --enable-lame --enable-mad" 

DEPENDS += "lame libmad"


python() {
	# Don't build, if we are building an ENTERPRISE distro
	enterprise = bb.data.getVar("ENTERPRISE_DISTRO", d, 1)
	if enterprise == "1":
		raise bb.parse.SkipPackage("gst-plugins-ugly will only build if ENTERPRISE_DISTRO != 1")
}
