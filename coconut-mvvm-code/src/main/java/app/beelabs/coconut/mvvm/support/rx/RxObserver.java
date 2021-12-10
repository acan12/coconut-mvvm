package app.beelabs.coconut.mvvm.support.rx;

import app.beelabs.coconut.mvvm.base.BaseDialog;
import app.beelabs.coconut.mvvm.base.BaseResponse;
import app.beelabs.coconut.mvvm.base.exception.NoConnectivityException;
import app.beelabs.coconut.mvvm.base.interfaces.IView;
import app.beelabs.coconut.mvvm.component.dialog.ProgressDialogComponent;
import app.beelabs.coconut.mvvm.support.dialog.CoconutAlertNoConnectionDialog;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxObserver<P extends BaseResponse> implements Observer {
    private IView iv;
    private String messageLoading;
    private long timeMilis;
    private int dialogType = DialogTypeEnum.DEFAULT;
    private static BaseDialog dialogNoconnection;

    public interface DialogTypeEnum {
        int DEFAULT = 0;
        int SPINKIT = 1;
    }

    public RxObserver(IView iv) {
        this.iv = iv;
    }

    public RxObserver(IView iv, String messageLoading) {
        this.iv = iv;
        this.messageLoading = messageLoading;
        this.timeMilis = 0;
    }

    public RxObserver(IView iv, String messageLoading, long timeMilis) {
        this.iv = iv;
        this.messageLoading = messageLoading;
        this.timeMilis = timeMilis;
    }

    public RxObserver setDialogType(int dialogType) {
        this.dialogType = dialogType;
        return this;
    }

    @Override
    public void onSubscribe(Disposable d) {
        ProgressDialogComponent.Companion.dismissProgressDialog(iv.getCurrentActivity());
//        SpinKitLoadingDialogComponent.dismissProgressDialog(iv.getCurrentActivity(), timeMilis);
        if (messageLoading != null) {
            switch (dialogType) {
                case DialogTypeEnum.DEFAULT:
                    ProgressDialogComponent.Companion.showProgressDialog(iv.getCurrentActivity(), messageLoading, false);
                    break;

//                case DialogTypeEnum.SPINKIT:
//                    SpinKitLoadingDialogComponent.showProgressDialog(iv.getCurrentActivity(), messageLoading, timeMilis);
//                    break;
            }
        }
    }

    @Override
    public void onNext(Object o) {
//        SpinKitLoadingDialogComponent.dismissProgressDialog(iv.getCurrentActivity(), timeMilis);
            ProgressDialogComponent.Companion.dismissProgressDialog(iv.getCurrentActivity());
    }

    @Override
    public void onError(Throwable e) {
        ProgressDialogComponent.Companion.dismissProgressDialog(iv.getCurrentActivity());
//        SpinKitLoadingDialogComponent.dismissProgressDialog(iv.getCurrentActivity(), timeMilis);
//
        if (e instanceof NoConnectivityException) {
            if (dialogNoconnection != null)
                if (dialogNoconnection.isShowing()) dialogNoconnection.dismiss();

            dialogNoconnection = new CoconutAlertNoConnectionDialog(iv.getCurrentActivity());
            dialogNoconnection.show();
            return;
        }

    }

    @Override
    public void onComplete() {
    }
}