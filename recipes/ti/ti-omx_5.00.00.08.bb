require ti-omx.inc

PV = "05_00_00_08"
PVExtra = "_pre-alpha1"

SRC_URI =+ "file://0001-Updated-to-support-EZ-SDK-Build.patch"
SRC_URI =+ "file://0001-ti-omx-Add-support-for-linking-with-FC-3.21.00.06.patch"

SRC_URI[omx.md5sum] = "1e974ca2a9de8c8c7f8e9039e124d99a"
SRC_URI[omx.sha256sum] = "f89b7244290b89fcf78f8aa7b403df4dc3153b2c1579e2eeba97737f2ba700e8"

