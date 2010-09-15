KVER = "2.6.28+2.6.29-rc3"
PSPREL = "02.01.00.02"

require linux-omap3-psp2.inc

SRCREV = "c40ce00e32082c57070fdba39c7d7cba3228d440"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/tmlind/linux-omap-2.6.git;protocol=git"

SRC_URI_append_omap3evm = " \
file://0001-usb-musb-sergei-s-8-patch-set.patch \
file://0002-usb-musb-adding-high-bandwidth-support.patch \
file://0003-usb-musb-fix-bug-in-musb_start_urb.patch \
file://0004-usb-musb-NAK-timeout-scheme-on-bulk-reserved-ep.patch \
file://0005-usb-musb-adding-nop-usb-transceiver.patch \
file://0006-usb-musb-registering-nop-xceiv-for-musb.patch \
file://0007-usb-musb-add-back-otg_get_transceiver.patch \
file://0008-usb-musb-fix-module-insert-issue.patch \
file://0009-usb-musb-init-musb-gadget_driver-to-null.patch \
file://0010-usb-musb-fix-vbuf-off-after-disconnect.patch \
file://0011-usb-musb-adding-musb-procfs-file.patch \
file://0012-usb-musb-sdma-for-all-the-rx-channels.patch \
file://0013-usb-musb-add-suspend-proc-entry-for-otg-testing.patch \
file://0014-usb-musb-remove-auto-selection-of-USB_SUSPEND-with.patch \
file://0015-usb-ehci-fix-ehci-rmmod-issue.patch \
file://0016-usb-ehci-fix-ehci-issue-when-built-as-module.patch \
file://0017-usb-ehci-update-for-mistral-daughter-card.patch \
file://0018-usb-ehci-EHCI-support-on-ES3.0.patch \
file://0019-OMAP2-3-clock-implement-clock-notifier-infrastructu.patch \
file://0020-OMAP-clock-add-notifier-infrastructure.patch \
file://0021-OMAP2-3-clock-store-planned-clock-rates-into-tempor.patch \
file://0022-OMAP2-3-clock-add-clk-post-rate-change-notifiers.patch \
file://0023-OMAP2-3-clock-add-clock-pre-rate-change-notificatio.patch \
file://0024-OMAP2-3-clock-add-clock-prepare-rate-change-notific.patch \
file://0025-OMAP2-3-clock-add-clock-abort-rate-change-notificat.patch \
file://0026-OMAP2-3-PM-create-the-OMAP-PM-interface-and-add-a-d.patch \
file://0027-OMAP2-3-omapdev-add-basic-omapdev-structure.patch \
file://0028-OMAP242x-omapdev-add-OMAP242x-omapdev-records.patch \
file://0029-OMAP243x-omapdev-add-OMAP243x-omapdev-records.patch \
file://0030-OMAP3xxx-omapdev-add-OMAP3xxx-omapdev-records.patch \
file://0031-OMAP2-3-omapdev-add-code-to-walk-the-omapdev-record.patch \
file://0032-OMAP-PM-counter-infrastructure.patch \
file://0033-OMAP-PM-Hook-into-PM-counters.patch \
file://0034-OMAP-PM-Add-closures-to-clkdm_for_each-and-pwrdm_f.patch \
file://0035-OMAP-PM-Add-pm-debug-counters.patch \
file://0036-OMAP-PM-debug-make-powerdomains-use-PM-debug-count.patch \
file://0037-OMAP-PM-debug-do-not-print-out-status-for-meta-pow.patch \
file://0038-OMAP-PM-debug-Add-PRCM-register-dump-support.patch \
file://0039-OMAP-PM-Add-definitions-for-ETK-pads-and-observabi.patch \
file://0040-OMAP-Debug-observability-and-ETK-padconf-implementa.patch \
file://0041-OMAP-Add-debug-observablity-debobs-Kconfig-item.patch \
file://0042-OMAP3-PM-GPMC-context-save-restore.patch \
file://0043-OMAP3-PM-GPIO-context-save-restore.patch \
file://0044-OMAP3-PM-I2C-context-save-restore.patch \
file://0045-OMAP3-PM-INTC-context-save-restore.patch \
file://0046-OMAP3-PM-PRCM-context-save-restore.patch \
file://0047-OMAP3-PM-Populate-scratchpad-contents.patch \
file://0048-OMAP3-PM-SCM-context-save-restore.patch \
file://0049-OMAP3-PM-SRAM-restore-function.patch \
file://0050-OMAP3-PM-handle-PER-NEON-CORE-in-idle.patch \
file://0051-OMAP3-PM-Restore-MMU-table-entry.patch \
file://0052-OMAP3-PM-MPU-off-mode-support.patch \
file://0053-OMAP3-PM-CORE-domain-off-mode-support.patch \
file://0054-OMAP3-PM-allow-runtime-enable-disable-of-OFF-mode.patch \
file://0055-OMAP3-3430SDP-minimal-kernel-defconfig.patch \
file://0056-OMAP-PM-sysfs-interface-for-enabling-voltage-off-i.patch \
file://0057-OMAP-PM-DMA-context-save-restore.patch \
file://0058-OMAP3-PM-CPUidle-Basic-support-for-C1-C2.patch \
file://0059-OMAP3-PM-CPUidle-Enables-state-C4.patch \
file://0060-OMAP3-PM-CPUidle-Enables-C3-and-C5.patch \
file://0061-OMAP3-PM-CPUidle-Safe-state-on-bm-activity.patch \
file://0062-OMAP3-PM-CPUidle-obey-enable_off_mode-flag.patch \
file://0063-OMAP3-PM-CPUidle-restrict-C-states-on-UART-activi.patch \
file://0064-OMAP3-PM-Fix-cpu-idle-init-sequencing.patch \
file://0065-OMAP-PM-off-mode-support-for-DMA-on-EMU-HS-devices.patch \
file://0066-OMAP3-SRAM-size-fix-for-HS-EMU-devices.patch \
file://0067-OMAP3-PM-off-mode-support-for-HS-EMU-devices.patch \
file://0068-OMAP3-PM-Enable-SDRAM-auto-refresh-during-sleep.patch \
file://0069-OMAP-SDRC-Add-new-register-definitions-for-SDRAM-c.patch \
file://0070-OMAP3-PM-SDRC-auto-refresh-workaround-for-off-mode.patch \
file://0071-OMAP-PM-Implement-get_last_off_on_transaction_id.patch \
file://0072-OMAP3-PM-Use-pwrdm_set_next_pwrst-instead-of-set_p.patch \
file://0073-OMAP3-SRF-Generic-shared-resource-f-w.patch \
file://0074-OMAP3-SRF-MPU-CORE-PD-latency-modeling.patch \
file://0075-OMAP3-SRF-omap3-srf-driver.patch \
file://0076-OMAP3-SRF-OMAP-PM-srf-implementation.patch \
file://0077-OMAP-SRF-Fixes-to-shared-resource-framework-Ver.3.patch \
file://0078-OMAP3-PM-Fix-wrong-sequence-in-suspend.patch \
file://0079-OMAP3-PM-decouple-PER-and-CORE-context-save-and-re.patch \
file://0080-PM-Added-three-PLL-registers-to-the-PRCM-context-sa.patch \
file://0081-PM-Changed-secure-RAM-storage-size-from-0x8000-to-0.patch \
file://0082-OMAP3-PM-Do-not-build-suspend-code-if-SUSPEND-is-n.patch \
file://0083-OMAP-PM-Build-fails-if-PM-is-not-enabled.patch \
file://0084-OMAP2-PM-Fix-omap2-build.patch \
file://0085-OMAP3-SRF-Add-CORE-rate-table-param-in-OMAP-PM.patch \
file://0086-OMAP3-SRF-Add-VDD1-VDD2-rate-tables-for-3430SDP.patch \
file://0087-OMAP3-SRF-Add-virt-clk-nodes-for-VDD1-VDD2.patch \
file://0088-OMAP3-SRF-Adds-OPP-Freq-res-s-in-SRF.patch \
file://0089-OMAP3-SRF-Update-OMAP-PM-layer.patch \
file://0090-OMAP3-SRF-Voltage-scaling-support.patch \
file://0091-OMAP3-SRF-VDD2-scaling-support.patch \
file://0092-OMAP3-SRF-Adds-sysfs-control-for-VDD1-VDD2-OPP-s.patch \
file://0093-OMAP3-PM-Replace-spinlocks-with-mutex-in-SRF.patch \
file://0094-OMAP3-PM-CPUFreq-driver-for-OMAP3.patch \
file://0095-OMAP3-PM-Update-the-min-defconfig-for-3430sdp.patch \
file://0096-OMAP3-SRF-Fix-crash-on-non-3430SDP-platforms-with-D.patch \
file://0097-Save-sram-context-after-changing-MPU-DSP-or-core-cl.patch \
file://0098-PM-Added-suspend-target-state-control-to-debugfs-fo.patch \
file://0099-OMAP2-3-PM-system_rev-omap_rev.patch \
file://0100-OMAP3-PM-Prevent-PER-from-going-OFF-when-CORE-is-g.patch \
file://0101-OMAP3-PM-Update-SSI-omapdev-record.patch \
file://0102-PM-OMAP3-Change-omap3_save_secure_ram-to-be-called.patch \
file://0103-OMAP3-PM-MPU-and-CORE-should-stay-awake-if-there-i.patch \
file://0104-OMAP3-PM-Scale-VDD2-OPP-for-VDD1-OPP3-and-higher.patch \
file://0105-OMAP3-GPIO-fixes-for-off-mode.patch \
file://0106-PM-OMAP3-Refreshed-DVFS-VDD1-control-against-lates.patch \
file://0107-ARM-MMU-add-a-Non-cacheable-Normal-executable-memo.patch \
file://0108-OMAP3-SRAM-mark-OCM-RAM-as-Non-cacheable-Normal-mem.patch \
file://0109-OMAP3-SRAM-add-ARM-barriers-to-omap3_sram_configure.patch \
file://0110-OMAP3-clock-add-interconnect-barriers-to-CORE-DPLL.patch \
file://0111-OMAP3-SRAM-clear-the-SDRC-PWRENA-bit-during-SDRC-fr.patch \
file://0112-OMAP3-SDRC-Add-166MHz-83MHz-SDRC-settings-for-the.patch \
file://0113-OMAP3-SDRC-initialize-SDRC_POWER-at-boot.patch \
file://0114-OMAP3-SRAM-renumber-registers-to-make-space-for-arg.patch \
file://0115-OMAP3-clock-only-unlock-SDRC-DLL-if-SDRC-clk-83MH.patch \
file://0116-OMAP3-clock-use-pr_debug-rather-than-pr_info-in.patch \
file://0117-OMAP3-clock-remove-wait-for-DPLL3-M2-clock-to-stabi.patch \
file://0118-OMAP3-clock-initialize-SDRC-timings-at-kernel-start.patch \
file://0119-OMAP3-clock-add-a-short-delay-when-lowering-CORE-cl.patch \
file://0120-OMAP3-clock-SDRC-program-SDRC_MR-register-during-SD.patch \
file://0121-OMAP3-SRAM-add-more-comments-on-the-SRAM-code.patch \
file://0122-OMAP3-SRAM-convert-SRAM-code-to-use-macros-rather-t.patch \
file://0123-OMAP3-Add-support-for-DPLL3-divisor-values-higher-t.patch \
file://0124-OMAP3-PM-Fixed-VDD2-control-to-work-from-both-sysf.patch \
file://0125-OMAP3-PM-Added-DVFS-OPP-locking-interface-for-VDD1.patch \
file://0126-OMAP3-Fix-rate-calculation-bug-in-omap3_select_tabl.patch \
file://0127-OMAP3-PM-Prevented-DVFS-state-switches-when-enabli.patch \
file://0128-OMAP3-PM-Enable-VDD2-OPP1.patch \
file://0129-OMAP3-PM-Fix-linker-error-without-CONFIG_PM-option.patch \
file://0130-PM-OMAP3-Removed-a-couple-of-unused-variables-from.patch \
file://0131-PM-OMAP3-Added-support-for-possibly-failing-clk_se.patch \
file://0132-Fix-omap_getspeed.patch \
file://0133-Make-sure-omap-cpufreq-driver-initializes-after-cpuf.patch \
file://0134-OMAP3-PM-fix-bug-where-UART0-and-1-were-not-resumi.patch \
file://0135-OMAP3-PM-Add-D2D-clocks-and-auto-idle-setup-to-PRC.patch \
file://0136-OMAP3-PM-D2D-clockdomain-supports-SW-supervised-tr.patch \
file://0137-OMAP3-PM-Ensure-MUSB-block-can-idle-when-driver-no.patch \
file://0138-PM-debug-Fix-problems-with-PM-timers.patch \
file://0139-OMAP3-PM-add-common-OPP-definitions-and-use-them-o.patch \
file://0140-OMAP3-PM-Wait-for-SDRC-ready-iso-a-blind-delay.patch \
file://0141-OMAP3-PM-Don-t-scale-voltage-in-C1-state.patch \
file://0142-OMAP3-McSPI-Adds-context-save-restore.patch \
file://0143-OMAP-I2C-Include-OMAP_I2C_SYSC_REG-in-save-and-rest.patch \
file://0144-OMAP2-3-GPIO-remove-recursion-in-IRQ-wakeup-path.patch \
file://0145-OMAP2-3-GPIO-generalize-prepare-for-idle.patch \
file://0146-OMAP3-GPIO-disable-GPIO-debounce-clocks-on-idle.patch \
file://0147-OMAP2-3-clockdomains-make-virt_opp_clkdm-available.patch \
file://0148-Revert-OMAP3-McSPI-Adds-context-save-restore.patch \
file://0149-OMAP3-PM-Save-and-restore-also-CM_CLKSEL1_PLL_IVA2.patch \
file://0150-OMAP3-McSPI-Adds-context-save-restore.patch \
file://0151-Add-support-for-OMAP35x-processors.patch \
file://0152-Runtime-check-for-OMAP35x.patch \
file://0153-Board-specific-updates.patch \
file://0154-DSS-New-display-subsystem-driver-for-OMAP2-3.patch \
file://0155-DSS-OMAPFB-fb-driver-for-new-display-subsystem.patch \
file://0156-DSS-Add-generic-DVI-panel.patch \
file://0157-DSS-support-for-Beagle-Board.patch \
file://0158-DSS-Sharp-LS037V7DW01-LCD-Panel-driver.patch \
file://0159-Signed-off-by-Tomi-Valkeinen-tomi.valkeinen-nokia.patch \
file://0160-DSS-Support-for-OMAP3-EVM-board.patch \
file://0161-DSS-Hacked-N810-support.patch \
file://0162-DSS-OMAPFB-allocate-fbmem-only-for-fb0-or-if-spes.patch \
file://0163-OMAPFB-remove-extra-omapfb_setup_overlay-call.patch \
file://0164-OMAPFB-fix-GFX_SYNC-to-be-compatible-with-DSS1.patch \
file://0165-DSS-Add-comments-to-FAKE_VSYNC-to-make-things-more.patch \
file://0166-OMAPFB-remove-extra-spaces.patch \
file://0167-DSS-fix-clk_get_usecount.patch \
file://0168-OMAPFB-remove-debug-print.patch \
file://0169-VRFB-testing.patch \
file://0170-OMAPFB-more-VRFB-hacks.patch \
file://0171-OMAPFB-still-more-VRFB-hacking.patch \
file://0172-V4L2-driver-added.patch \
file://0173-Only-made-copmipled-with-Hariks-changes.patch \
file://0174-VRFB-rotation-at-compile-time-supported.patch \
file://0175-Minor-Fixes-to-V4L2-driver.patch \
file://0176-Minor-Fixes-in-DSS-library.patch \
file://0177-Kconfig-option-added-to-select-overlay-manager.patch \
file://0178-Merged-Latest-Tomi-s-changes.patch \
#file://0179-file-mode-restored-back.patch \
file://0180-old-FBDEV-made-working.patch \
file://0181-DVI-720P-and-480P-support-added.patch \
file://0182-VRFB-address-calculation-bug-fixed.patch \
file://0183-Changed-V4L2-file-operations-according-to-2.6.29.patch \
file://0184-Bug-Solved-Rotation-Bug.patch \
file://0185-Bug-Solved-Get-rotation-not-working-for-90-and-270.patch \
file://0186-Bug-Solved-Compile-time-option-to-select-TV-mode.patch \
file://0187-Changed-the-compile-time-option-to-select-LCD-or-TV.patch \
file://0188-Bug-Solved-VRFB-rotation-not-working-on-DVI-output.patch \
file://0189-Bug-solved-Colors-not-coming-proper-on-DVI-output.patch \
file://0190-V4L-Int-if-Dummy-slave.patch \
file://0191-v4l2-int-device-add-support-for-VIDIOC_QUERYMENU.patch \
file://0192-V4L2-Add-COLORFX-user-control.patch \
file://0193-OMAP34XX-CAM-Resources-fixes.patch \
file://0194-OMAP-CAM-Add-ISP-user-header-and-register-defs.patch \
file://0195-OMAP-CAM-Add-ISP-gain-tables.patch \
file://0196-OMAP-CAM-Add-ISP-Front-end.patch \
file://0197-OMAP-CAM-Add-ISP-Back-end.patch \
file://0198-OMAP-CAM-Add-ISP-SCM.patch \
file://0199-OMAP-CAM-Add-ISP-CSI2-API.patch \
file://0200-OMAP-CAM-Add-ISP-Core.patch \
file://0201-OMAP34XXCAM-Add-driver.patch \
file://0202-OMAP-CAM-Add-MT9P012-Sensor-Driver.patch \
file://0203-OMAP-CAM-Add-DW9710-Lens-Driver.patch \
file://0204-OMAP34XX-CAM-Add-Sensors-Support.patch \
file://0205-V4L-Query-slave-info.patch \
file://0206-OMAP3ISP-REGS-Fix-ISPCCDC_SDOFST_FOFST-definition.patch \
file://0207-OMAP3ISP-REGS-Add-CCDC-SBL-status-regs.patch \
file://0208-OMAP3ISP-REGS-Add-configuration-id-counters.patch \
file://0209-OMAP3ISP-Gain-Tables-Better-cfa-coefficient-table.patch \
file://0210-OMAP3ISP-Frontend-Add-API-for-CCDC-SBL-busy.patch \
file://0211-OMAP3ISP-Frontend-Now-using-video-port-for-RAW-cap.patch \
file://0212-OMAP3ISP-Frontend-fix-ISPCCDC_SDOFST_FOFST-clearin.patch \
file://0213-OMAP3ISP-Frontend-fix-colors-bayer-phase-in-raw.patch \
file://0214-OMAP3ISP-Frontend-Sensor-pattern-and-VP-fix-for-YU.patch \
file://0215-OMAP3ISP-Frontend-Fix-output-horizontal-pixel-coun.patch \
file://0216-OMAP3ISP-Frontend-Remaining-Syncup-with-Nokia-Code.patch \
file://0217-OMAP3ISP-Frontend-Small-cleanups.patch \
file://0218-OMAP3ISP-Frontend-Change-default-DC-substraction-v.patch \
file://0219-OMAP3ISP-Backend-Use-correct-number-of-lines-in-pr.patch \
file://0220-OMAP3ISP-Backend-Fix-for-default-WB-coeficients-fo.patch \
file://0221-OMAP3ISP-Backend-Better-preview-default-values.patch \
file://0222-OMAP3ISP-Backend-Resizer-cleanup.patch \
file://0223-OMAP3ISP-Backend-Always-do-workaround.patch \
file://0224-OMAP3ISP-Backend-Correct-applying-of-RGB2RGB-RGB2.patch \
file://0225-OMAP3ISP-SCM-Add-configuration-id-counters.patch \
file://0226-OMAP3ISP-SCM-H3a-Aewb-first-frame-statistics-fix.patch \
file://0227-OMAP3ISP-SCM-WB-coefficients-update-via-h3a-for-co.patch \
file://0228-OMAP3ISP-SCM-Allow-unloading-the-module-without-a.patch \
file://0229-OMAP3ISP-Core-Fix-crop.patch \
file://0230-OMAP3ISP-Core-Rewrite-ISR-and-buff-mgmt.patch \
file://0231-OMAP3ISP-Core-Flush-buffers-also-when-queueing.patch \
file://0232-OMAP3ISP-Core-Fix-isp_s_fmt_cap-crop-for-raw-captu.patch \
file://0233-OMAP3ISP-Core-Enable-Preview-Callback.patch \
file://0234-OMAP3ISP-Core-Do-idle-mode-settings-in-the-ISP-dri.patch \
file://0235-OMAP3ISP-Core-Move-clk_gets-to-isp_init.patch \
file://0236-OMAP3ISP-Core-Clean-up-temporary-buffer-workaround.patch \
file://0237-OMAP3ISP-Core-Make-isp_interface_config-as-part-of.patch \
file://0238-OMAP3ISP-Core-Remove-isp_request_interface-and-dum.patch \
file://0239-OMAP3ISP-Core-Remove-isp_configure_interface_bridg.patch \
file://0240-OMAP3ISP-Core-Move-temporary-buffer-stuff-to-struc.patch \
file://0241-OMAP3ISP-Core-More-cleanups.patch \
file://0242-OMAP3ISP-Core-Remove-isp_get_xclk-and-make-isp_-_c.patch \
file://0243-OMAP3ISP-Core-compile-fix.patch \
file://0244-OMAP3ISP-Core-Fix-error-checking-for-isp_addr-in-i.patch \
file://0245-OMAP3ISP-Core-MMU-Small-cleanup.patch \
file://0246-OMAP3ISP-Core-Remove-idle-mode-settings-from-mmu.patch \
file://0247-omap3isp-Add-interface-type-ISP_NONE-for-preview.patch \
file://0248-OMAP34XXCAM-Implement-VIDIOC_ENUM_SLAVES.patch \
file://0249-omap34xxcam-Get-format-from-the-sensor-in-the-begin.patch \
file://0250-omap34xxcam-Handle-s_fmt-from-multiple-sources-prop.patch \
file://0251-omap34xxcam-Requeue-faulty-buffers.patch \
file://0252-omap34xxcam-isp-updates.patch \
file://0253-omap34xxcam-Start-ISP-after-sensor.patch \
file://0254-omap34xxcam-Don-t-do-ISP-idle-mode-settings.patch \
file://0255-omap34xxcam-do-consult-isp_vbq_setup.patch \
file://0256-omap34xxcam-Power-down-slaves-at-streamoff-unless-v.patch \
file://0257-omap34xxcam-Remove-isp_buf_init.patch \
file://0258-omap34xxcam-Fix-module-author-e-mail.patch \
file://0259-omap34xxcam-Get-rid-of-hw-resources.patch \
file://0260-OMAP3430SDP-MT9P012-Get-rid-of-vdint-01-_timing.patch \
file://0261-OMAP-CAM-Add-OV3640-Sensor-Driver.patch \
file://0262-OMAP34XX-CAM-Add-OV3640-Sensor-Support.patch \
file://0263-OMAP3430SDP-CAM-Add-wait_hs_vs-field-in-isp-if-con.patch \
file://0264-Pad-configuration-for-OMAP3EVM-Multi-Media-Daughter.patch \
file://0265-OMAP3EVM-Multi-Media-Daughter-Card-Support.patch \
file://0266-OMAP3-ISP-Camera-Added-BT656-support-ontop-of-Nokia.patch \
file://0267-MMDC-patch-made-it-work-with-new-ISP-Camera-Nokia-fi.patch \
file://0268-Camera-Kconfig-option-changed-from-V4L2-DSS.patch \
file://0269-Bug-SDOCM00053646-YUYV-support-in-Capture-is-broken.patch \
file://0270-Put-support-for-TPS6235x-support.patch \
file://0271-Build-TPS6235x-based-PR785-board-support.patch \
file://0272-Fix-the-MMC-SD-hotplug-issue.patch \
file://0273-ALSA-ASOC-Added-OMAP3EVM-support.patch \
file://0274-ASoC-OMAP-Initialize-XCCR-and-RCCR-registers-in-Mc.patch \
file://0275-usb-defconfig-for-musb-and-ehci.patch \
file://0276-usb-defconfig-for-usb-audio.patch \
file://0277-usb-defconfig-for-usb-video.patch \
file://0278-usb-ehci-fix-companion-port-ownership-issue.patch \
file://0279-usb-musb-disable-ping-token-in-status-phase-of-con.patch \
file://0280-enable-Audio-as-part-of-OMAP3EVM-default-configurati.patch \
file://0281-Bug-SDOCM00053650-fixed-issue-of-g_input-tied-to-CV.patch \
file://0282-Backlight-driver-for-omap3evm.patch \
file://0283-Resizer-and-Previewer-driver-added-to-commit.patch \
file://0284-Resizer-bug-fixes-on-top-of-1.0.2-release.patch \
file://0285-backlight-driver-made-worked-with-DSS2.patch \
file://0286-defconfig-updated-for-video.patch \
file://0287-twl-API-moved-to-comiple-time-macro.patch \
file://0288-Audio-ASOC-OMAP3-EVM-support-added.patch \
file://0289-Enable-Video-driver-as-part-of-omap3evm-default-conf.patch \
file://0290-Add-macros-to-identify-Si-versions.patch \
file://0291-DSS2-library-enabled-from-v4l2-driver.patch \
file://0292-Interdependancy-between-ISP-and-Camera-removed.patch \
file://0293-PM-Fix-compile-error-with-CPU-Idle-enabled.patch \
file://0294-SMC911x-temporary-workaround-remove-phy-power-do.patch \
file://0295-Added-support-for-Background-color-in-DSS-library.patch \
file://0296-Added-Background-color-support-to-V4L2-driver.patch \
file://0297-Source-color-keying-support-in-DSS-library.patch \
file://0298-Added-src-color-keying-support-in-V4L2-driver.patch \
file://0299-Added-Alpha-blending-support-in-DSS-Library.patch \
file://0300-Alpha-blending-support-for-V4L2-driver.patch \
file://0301-Alpha-blending-support-for-frame-buffer-driver.patch \
file://0302-Merge-Conflict-fixed-in-arch-arm-plat-omap-dss-dpi.c.patch \
file://0303-Get-alpha-blending-support-added-in-DSS-Library.patch \
file://0304-Get-alpha-blending-support-added-in-V4L2-driver.patch \
"
