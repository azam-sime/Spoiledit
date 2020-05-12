package com.spoiledit.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.spoiledit.R;
import com.spoiledit.constants.Type;
import com.spoiledit.listeners.OnFileSourceChoiceListener;
import com.spoiledit.listeners.OnReportActionListener;

import java.util.Objects;

public final class DialogUtils {
    public static final String TAG = DialogUtils.class.getCanonicalName();
    private static AlertDialog responseInfoDialog;

    private DialogUtils() {

    }

    public static void showFailure(Activity activity, int stringResId) {
        showFailure(activity, AppUtils.getString(activity, stringResId));
    }


    public static void showFailure(Activity activity, String message) {
        createCancelableNoActionDialog(activity, Type.Info.SORRY, message);
    }

    public static void showFailure(Activity activity, String message, Runnable positive) {
        createNonCancelableSingleActionDialog(activity, Type.Info.SORRY, message, "Okay", positive);
    }


    public static void showSuccess(Activity activity, String message) {
        createCancelableNoActionDialog(activity, Type.Info.SMILES, message);
    }

    public static void showSuccess(Activity activity, String message, Runnable positive) {
        createNonCancelableSingleActionDialog(activity, Type.Info.SMILES, message, "Proceed", positive);
    }


    public static void showWarning(Activity activity, String message) {
        createCancelableNoActionDialog(activity, Type.Info.OOPS, message);
    }

    public static void showWarning(Activity activity, String message, Runnable positive) {
        createNonCancelableSingleActionDialog(activity, Type.Info.OOPS, message, "Okay", positive);
    }


    public static void showConfirm(Activity activity, String message) {
        createCancelableNoActionDialog(activity, Type.Info.SURE, message);
    }

    public static void showConfirm(Activity activity, String message, Runnable positive) {
        createNonCancelableSingleActionDialog(activity, Type.Info.SURE, message, "Confirm", positive);
    }


    public static void createCancelableNoActionDialog(Context context, int infoType, String infoMessage) {
        createCancelableDialog(context, infoType, infoMessage, null, null, null, null);
    }

    public static void createCancelableSingleActionDialog(Context context, int infoType, String infoMessage,
                                                          String pText, Runnable positive) {
        createCancelableDialog(context, infoType, infoMessage, pText, positive, null, null);
    }

    public static void createCancelableDialog(Context context, int infoType, String infoMessage,
                                              String pText, Runnable positive, String nText, Runnable negative) {
        createDialog(context, true, infoType, infoMessage, pText, positive, nText, negative);
    }


    public static void createNonCancelableNoActionDialog(Context context, int infoType, String infoMessage) {
        createNonCancelableDialog(context, infoType, infoMessage, null, null, null, null);
    }

    public static void createNonCancelableSingleActionDialog(Context context, int infoType, String infoMessage,
                                                             String pText, Runnable positive) {
        createNonCancelableDialog(context, infoType, infoMessage, pText, positive, null, null);
    }

    public static void createNonCancelableDialog(Context context, int infoType, String infoMessage,
                                                 String pText, Runnable positive, String nText, Runnable negative) {
        createDialog(context, false, infoType, infoMessage, pText, positive, nText, negative);
    }


    private static void createDialog(Context context, boolean cancelable, int infoType, String infoMessage,
                                     String pText, Runnable positive, String nText, Runnable negative) {
        createDialog(context, cancelable, infoType, infoMessage, R.drawable.popcorn,
                pText, positive, nText, negative);
    }


    private static void createDialog(Context context, boolean cancelable, int infoType, String infoMessage,
                                     int infoDrawable,
                                     String pText, Runnable positive, String nText, Runnable negative) {
        try {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.layout_info, null);
            builder.setView(view);

            ImageView ivInfoIcon = view.findViewById(R.id.iv_info_icon);

            TextView tvTitle = view.findViewById(R.id.tv_info_title);
            TextView tvMessage = view.findViewById(R.id.tv_info_message);

            MaterialButton btnPositive = view.findViewById(R.id.btn_positive);
            MaterialButton btnNegative = view.findViewById(R.id.btn_negative);

            switch (infoType) {
                case Type.Info.OOPS: {
                    tvTitle.setText(context.getString(R.string.oops));
                    if (StringUtils.isInvalid(pText))
                        pText = context.getString(R.string.ok);
                }
                break;
                case Type.Info.SORRY: {
                    tvTitle.setText(context.getString(R.string.sorry));
                    if (StringUtils.isInvalid(pText))
                        pText = context.getString(R.string.ok);
                }
                break;
                case Type.Info.SMILES: {
                    tvTitle.setText(context.getString(R.string.smiles));
                    if (StringUtils.isInvalid(pText))
                        pText = context.getString(R.string.ok);
                }
                break;
                case Type.Info.HEY: {
                    tvTitle.setText(context.getString(R.string.hey));
                    if (StringUtils.isInvalid(pText))
                        pText = context.getString(R.string.ok);
                }
                break;
                case Type.Info.SURE: {
                    tvTitle.setText(context.getString(R.string.hey_you_sure));
                    if (StringUtils.isInvalid(pText))
                        pText = context.getString(R.string.yes_im);
                    if (StringUtils.isInvalid(nText))
                        nText = context.getString(R.string.no_cancel);
                }
                break;
                case Type.Info.LOGOUT: {
                    tvTitle.setText(context.getString(R.string.hey));
                    if (StringUtils.isInvalid(pText))
                        pText = context.getString(R.string.logout);
                    if (StringUtils.isInvalid(nText))
                        nText = context.getString(R.string.cancel);
                }
                break;
                case Type.Info.UPDATE: {
                    tvTitle.setText(context.getString(R.string.update_available));
                    if (StringUtils.isInvalid(pText))
                        pText = context.getString(R.string.update);
                    infoMessage = context.getString(R.string.update_info);
                }
                break;
                case Type.Info.CUSTOM: {
                    tvTitle.setText(context.getString(R.string.hey));
                }
                break;
            }

            builder.setCancelable(cancelable);

            tvMessage.setText(infoMessage);
            ivInfoIcon.setImageResource(infoDrawable);

            btnPositive.setText(pText);
            btnPositive.setOnClickListener(v -> {
                if (positive != null)
                    positive.run();

                if (responseInfoDialog != null) {
                    responseInfoDialog.cancel();
                    responseInfoDialog = null;
                }
            });

            if (nText != null) {
                btnNegative.setText(nText);
                btnNegative.setOnClickListener(v -> {
                    if (negative != null)
                        negative.run();

                    if (responseInfoDialog != null) {
                        responseInfoDialog.cancel();
                        responseInfoDialog = null;
                    }
                });
            } else {
                ViewUtils.hideViews(btnNegative);
            }

            responseInfoDialog = builder.create();
            responseInfoDialog.show();

            resizeDialogToMatchWindow(context, responseInfoDialog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showFileSources(Context context, final OnFileSourceChoiceListener sourceChoiceListener) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_file_source);

        dialog.findViewById(R.id.view_camera).setOnClickListener(view -> {
            if (sourceChoiceListener != null) {
                sourceChoiceListener.onCameraChosen();
            }
            dialog.dismiss();
        });
        dialog.findViewById(R.id.view_gallery).setOnClickListener(view -> {
            if (sourceChoiceListener != null) {
                sourceChoiceListener.onGalleryChosen();
            }
            dialog.dismiss();
        });
        dialog.findViewById(R.id.view_file_manager).setOnClickListener(view -> {
            if (sourceChoiceListener != null) {
                sourceChoiceListener.onFileManagerChosen();
            }
            dialog.dismiss();
        });

        dialog.setCancelable(true);
        dialog.show();

        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        resizeDialogToMatchWindow(context, dialog);
    }

    private static int reportType = -1;
    public static void createReportDialog(Context context, String title, OnReportActionListener onReportActionListener) {
        try {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.layout_report, null);

            builder.setView(view);
            builder.setCancelable(false);

            ((TextView) view.findViewById(R.id.tv_info_title)).setText(title);
            EditText etExplanation = view.findViewById(R.id.et_report_explanation);

            reportType = -1;
            ((RadioGroup) view.findViewById(R.id.rg_report)).setOnCheckedChangeListener(
                    (group, checkedId) -> {
                        if (checkedId == R.id.rbtn_spam)
                            reportType = 1;
                        else if (checkedId == R.id.rbtn_wrong)
                            reportType = 2;
                        else if (checkedId == R.id.rbtn_other)
                            reportType = 3;
                    });

            view.findViewById(R.id.iv_close).setOnClickListener(v -> {
                responseInfoDialog.dismiss();
            });
            view.findViewById(R.id.btn_positive).setOnClickListener(v -> {
                if (reportType == -1)
                    showToast(context, "Please select report type!");
                else if (reportType == 2 && StringUtils.isInvalid(etExplanation))
                    showToast(context, "Please add explanation!");
                else {
                    responseInfoDialog.dismiss();
                    if (onReportActionListener != null) {
                        onReportActionListener.onContentReported(
                                reportType, etExplanation.getText().toString().trim()
                        );
                    }
                }
            });

            responseInfoDialog = builder.create();
            responseInfoDialog.show();

            resizeDialogToMatchWindow(context, responseInfoDialog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void resizeDialogToMatchWindow(Context context, Dialog dialog) {
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 1.00);
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
