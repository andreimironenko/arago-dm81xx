require ti-syslink.inc

PV = "02_00_00_67"
PVwithdots = "02.00.00.67"
PVextra = "_alpha2"

# This is an internal engineering release. Do not use this release by default.
DEFAULT_PREFERENCE = "-1"
HTTP_PROXY_IGNORE="bangsdowebsvr01.india.ti.com:8060"

SRC_URI = "http://bangsdowebsvr01.india.ti.com:8060/SysLink_int/${PV}${PVextra}/exports/syslink_${PV}${PVextra}.tar.gz;name=syslinktarball" 

SRC_URI[syslinktarball.md5sum] = "375c975fad16cf75cb918b6a90073e68"
SRC_URI[syslinktarball.sha256sum] = "055f7c650fffd178b07a163c10699a6817425fd6d310299fbe1bcbde6ae27f23"

