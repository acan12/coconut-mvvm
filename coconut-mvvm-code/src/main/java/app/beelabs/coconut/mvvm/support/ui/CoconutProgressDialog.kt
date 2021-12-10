package app.beelabs.coconut.mvvm.support.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnShowListener
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import app.beelabs.coconut.mvvm.R
import com.google.android.material.button.MaterialButton
import java.lang.IllegalArgumentException
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.*

/**
 * reference: https://github.com/techinessoverloaded/progress-dialog
 */

/**
 * A highly customisable ProgressDialog Class for Android API Level 24 and above.
 * Built upon Android's AlertDialog. So no Compatibility and Reliability issues.
 * It provides a neat Material Design UI for ProgressDialog and supports both Determinate and Indeterminate ProgressBars.
 * It also has support for Dark Theme.
 * It can also have a NegativeButton.
 * It can be customized according to User's needs using the provided Methods.
 */
class CoconutProgressDialog {
    @SuppressLint("NewApi")
    @Retention(RetentionPolicy.SOURCE)
    @IntDef(THEME_LIGHT, THEME_DARK, THEME_FOLLOW_SYSTEM)
    annotation class ThemeConstant

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
        MODE_INDETERMINATE, MODE_DETERMINATE
    )
    annotation class ModeConstant

    private val context: Context
    private var titleView: TextView? = null
    private var textViewIndeterminate: TextView? = null
    private var textViewDeterminate: TextView? = null
    private var progressTextView: TextView? = null
    private var negativeButton: MaterialButton? = null
    private var progressBarDeterminate: ProgressBar? = null
    private var progressBarIndeterminate: ProgressBar? = null
    private var progressDialog: AlertDialog? = null
    private var dialogLayout: ConstraintLayout? = null

    /**
     * Returns the Current Mode of ProgressDialog.
     * @return The current Mode of ProgressDialog ([.MODE_DETERMINATE] or [.MODE_INDETERMINATE]).
     */
    @get:ModeConstant
    var mode = 0
        private set
    internal var theme = 0
    private var incrementAmt = 0
    private var progressViewMode = 0
    private var cancelable = false
    private var autoThemeEnabled = false

    /**
     * Simple Constructor accepting only the Activity Level Context as Argument.
     * Theme is set as Light Theme by Default (which can be changed later using [.setTheme]).
     * Mode is set as Indeterminate by Default (which can be changed later using [.setMode]).
     */
    @SuppressLint("NewApi")
    constructor(context: Context) {
        this.context = context
        initialiseDialog(THEME_LIGHT, MODE_INDETERMINATE)
    }

    /**
     * A Constructor accepting the Activity Level Context and Theme Constant as Arguments.
     * Theme is set as Light Theme if [.THEME_LIGHT] is passed (This can be changed later using [.setTheme]).
     * Theme is set as Dark Theme if [.THEME_DARK] is passed (This can be changed later using [.setTheme]).
     * Theme is automatically decided at runtime according to System's Theme if [.THEME_FOLLOW_SYSTEM] is passed (This can be changed later using [.setTheme]).
     * NOTE : [.THEME_FOLLOW_SYSTEM] can be used starting from Android API Level 31 only.
     * Mode is set as Indeterminate by Default (which can be changed later using [.setMode]).
     */
    @SuppressLint("NewApi")
    constructor(context: Context, @ThemeConstant themeConstant: Int) {
        this.context = context
        initialiseDialog(themeConstant, MODE_INDETERMINATE)
    }

    /**
     * A Constructor accepting the Mode Constant, Activity Level Context and Theme Constant as Arguments.
     * Mode is set as Determinate if [] is passed (This can be changed later using [.setMode]).
     * Mode is set as Indeterminate if [.MODE_INDETERMINATE] is passed (This can be changed later using [.setMode]).
     * Theme is set as Light Theme if [.THEME_LIGHT] is passed (This can be changed later using [.setTheme]).
     * Theme is set as Dark Theme if [.THEME_DARK] is passed (This can be changed later using [.setTheme]).
     * Theme is automatically decided at runtime according to System's Theme if [.THEME_FOLLOW_SYSTEM] is passed (This can be changed later using [.setTheme]).
     * NOTE : [.THEME_FOLLOW_SYSTEM] can be used starting from Android API Level 31 only.
     */
    @SuppressLint("NewApi")
    constructor(
        @ModeConstant modeConstant: Int,
        context: Context,
        @ThemeConstant themeConstant: Int
    ) {
        this.context = context
        initialiseDialog(themeConstant, modeConstant)
    }

    /**
     * A Constructor accepting the Mode Constant and Activity Level Context as Arguments.
     * Mode is set as Determinate if [.MODE_DETERMINATE] is passed (This can be changed later using [.setMode]).
     * Mode is set as Indeterminate if [.MODE_INDETERMINATE] is passed (This can be changed later using [.setMode]).
     * Theme is set as Light Theme by Default (which can be changed later using [.setTheme]).
     */
    @SuppressLint("NewApi")
    constructor(@ModeConstant modeConstant: Int, context: Context) {
        this.context = context
        initialiseDialog(THEME_LIGHT, modeConstant)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun initialiseDialog(@ThemeConstant themeValue: Int, @ModeConstant modeValue: Int) {
        val builder = AlertDialog.Builder(context)
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_progressdialog, null)
        dialogLayout = view.findViewById(R.id.dialog_layout)
        titleView = view.findViewById(R.id.title_progressDialog)
        textViewDeterminate = view.findViewById(R.id.textView_determinate)
        textViewIndeterminate = view.findViewById(R.id.textView_indeterminate)
        progressBarIndeterminate = view.findViewById(R.id.progressbar_indeterminate)
        progressBarDeterminate = view.findViewById(R.id.progressbar_determinate)
        progressTextView = view.findViewById(R.id.ProgressTextView)
        negativeButton = view.findViewById(R.id.negativeBtn)
        setTheme(themeValue)
        setMode(modeValue)
        builder.setView(view)
        progressDialog = builder.create()
        if (progressDialog!!.window != null) {
            progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        setCancelable(false)
    }

    /**
     * Sets/Changes the mode of ProgressDialog which is [.MODE_INDETERMINATE] by Default.
     * If you're going to use only one Mode constantly, this method is not needed. Instead, use an appropriate Constructor to set the required Mode during Instantiation.
     * @param modeConstant The Mode Constant to be passed as Argument ([.MODE_DETERMINATE] or [.MODE_INDETERMINATE]).
     * @return true if the passed modeConstant is valid and is set. false if the passed Mode is the current Mode or if modeConstant is invalid.
     */
    fun setMode(@ModeConstant modeConstant: Int): Boolean {
        return if (modeConstant == mode) false else when (modeConstant) {
            MODE_DETERMINATE -> {
                textViewIndeterminate!!.visibility = View.GONE
                progressBarIndeterminate!!.visibility = View.GONE
                textViewDeterminate!!.visibility = View.VISIBLE
                progressBarDeterminate!!.visibility = View.VISIBLE
                progressViewMode = SHOW_AS_PERCENT
                progressTextView!!.visibility = View.VISIBLE
                incrementAmt = if (incrementAmt == 0) 1 else incrementAmt
                mode = modeConstant
                true
            }
            MODE_INDETERMINATE -> {
                textViewDeterminate!!.visibility = View.GONE
                progressBarDeterminate!!.visibility = View.GONE
                progressTextView!!.visibility = View.GONE
                textViewIndeterminate!!.visibility = View.VISIBLE
                progressBarIndeterminate!!.visibility = View.VISIBLE
                mode = modeConstant
                true
            }
            else -> false
        }
    }

    /**
     * Sets/Changes the Theme of ProgressDialog which is [.THEME_LIGHT] by Default.
     * If you're going to use only one Theme constantly, this method is not needed. Instead, use an appropriate Constructor to set the required Theme during Instantiation.
     * @param themeConstant The Theme Constant to be passed.Use [.THEME_LIGHT] for Light Mode. Use [.THEME_DARK] for Dark Mode. Use [.THEME_FOLLOW_SYSTEM] for AutoTheming based on System theme (can be used starting from Android 11 (API Level 31) ONLY).
     * @return true if the passed themeConstant is valid and is set. false if the passed Theme is the current Theme or if themeConstant is invalid.
     * @throws IllegalArgumentException if [.THEME_FOLLOW_SYSTEM] is passed as Argument to this method in Android Versions lower than Android 11 (API Level 30)}.
     */
    @RequiresApi(Build.VERSION_CODES.R)
    @Throws(IllegalArgumentException::class)
    fun setTheme(@ThemeConstant themeConstant: Int): Boolean {
        return if (themeConstant == theme) false else when (themeConstant) {
            THEME_DARK, THEME_LIGHT -> {
                autoThemeEnabled = false
                setThemeInternal(themeConstant)
            }
            THEME_FOLLOW_SYSTEM -> {
                require(isAboveOrEqualToAnd11) { "THEME_FOLLOW_SYSTEM can be used starting from Android 11 (API Level 30) only !" }
                autoThemeEnabled = true
                true
            }
            else -> false
        }
    }

    /**
     * Returns the Current Theme of ProgressDialog.
     * @return The current Theme of ProgressDialog ([.THEME_LIGHT] or [.THEME_DARK] or [.THEME_FOLLOW_SYSTEM](starting from Android 11)).
     */
    @RequiresApi(Build.VERSION_CODES.R)
    @ThemeConstant
    fun getTheme(): Int {
        return if (autoThemeEnabled) THEME_FOLLOW_SYSTEM else theme
    }

    /**
     * Sets the Text to be displayed alongside ProgressBar.
     * Message is "Loading" by Default. This can be used if null is passed as parameter.
     * Alternative to [.setMessage].
     * @param message The Text to be displayed inside ProgressDialog. If null is passed, "Loading" will be set.
     */
    fun setMessage(message: CharSequence?) {
        if (message != null) {
            if (!isDeterminate) {
                textViewIndeterminate!!.text = message
            } else {
                textViewDeterminate!!.text = message
            }
        }
    }

    /**
     * Sets the Text from the resID provided, to be displayed alongside ProgressBar.
     * Message is "Loading" by Default.
     * Alternative to [.setMessage].
     * @param messageResID The resource id for the string resource.
     */
    fun setMessage(@StringRes messageResID: Int) {
        setMessage(context.getText(messageResID).toString())
    }

    /**
     * Sets the Title of ProgressDialog.
     * This is "ProgressDialog" by Default. This can be used by passing null as parameter.
     * Title is Hidden by Default. This Method makes the Title Visible even if null is passed as parameter.
     * Title will be made visible automatically if [.setNegativeButton] or
     * [.setNegativeButton] was used before.
     * Alternative to [.setTitle].
     * @param title The text to be set as the Title of ProgressDialog. If null is passed, "ProgressDialog" will be set.
     */
    fun setTitle(title: CharSequence?) {
        if (title != null) titleView!!.text = title
        if (isGone(titleView)) titleView!!.visibility = View.VISIBLE
    }

    /**
     * Sets the Title of ProgressDialog using the String resource given.
     * Title is "ProgressDialog" by Default.
     * Title is Hidden by Default. This Method makes the Title Visible even if "null" is passed as argument.
     * Title will be made visible automatically if [.setNegativeButton] or
     * [.setNegativeButton] was used before.
     * Alternative to [.setTitle].
     * @param titleResID The resource id of the string resource.
     */
    fun setTitle(@StringRes titleResID: Int) {
        setTitle(context.getText(titleResID).toString())
    }

    /**
     * Hides the Title of ProgressDialog.
     * Title is Hidden by Default.
     * Use this method only if you have used [.setTitle] before.
     * This method won't work if [.setNegativeButton] or
     * [.setNegativeButton] was used before.
     * @return true if Title is Hid. false if the Title is already Hidden or if NegativeButton is used.
     */
    fun hideTitle(): Boolean {
        if (!isGone(negativeButton)) return false
        if (isVisible(titleView)) titleView!!.visibility = View.GONE
        return true
    }

    /**
     * Sets the Progress Value of Determinate ProgressBar.
     * Can be used only in [.MODE_DETERMINATE] Mode.
     * If the parameter progress is greater than MaxValue, MaxValue will be set as Progress.
     * @param progress The Integral Progress Value to be set in Determinate ProgressBar.
     * @return true if Mode is [.MODE_DETERMINATE] and Progress is set. false otherwise.
     * @see .incrementProgress
     */
    @SuppressLint("NewApi")
    fun setProgress(progress: Int): Boolean {
        return if (isDeterminate) {
            progressBarDeterminate!!.setProgress(progress, true)
            setProgressText()
            true
        } else {
            false
        }
    }

    /**
     * Sets the Increment Offset Value for Determinate ProgressBar.
     * The value is 1 by Default.
     * Should be called before calling [.incrementProgress] method.
     * Can be used only in [.MODE_DETERMINATE] Mode.
     * @param increment The Integral Offset Value for Incrementing Progress in Determinate ProgressBar.
     * @return true if Mode is [.MODE_DETERMINATE] and Progress is set. false otherwise.
     * @see .incrementProgress
     */
    fun setIncrementValue(increment: Int): Boolean {
        return if (isDeterminate) {
            incrementAmt = increment
            true
        } else {
            false
        }
    }

    /**
     * Returns the Current Increment Offset Value.
     * Can be used only in [.MODE_DETERMINATE] Mode.
     * @return The Current Increment Offset Value of Determinate ProgressBar if Mode is [.MODE_DETERMINATE]. Else -1 is returned.
     */
    val incrementValue: Int
        get() = if (isDeterminate) incrementAmt else -1

    /**
     * Increments the Progress Value of Determinate ProgressBar using the Offset Value set using [.setIncrementValue].
     * Can be used only in [) Mode.][.MODE_DETERMINATE]
     */
    fun incrementProgress(): Boolean {
        return if (isDeterminate) {
            progressBarDeterminate!!.incrementProgressBy(incrementAmt)
            setProgressText()
            true
        } else {
            false
        }
    }

    /**
     * Sets the Maximum value of Determinate ProgressBar.
     * The Default MaxValue of Determinate ProgressBar is 100.
     * Can be used only in [.MODE_DETERMINATE] Mode.
     * It is advised to use this method before calling [.setProgress] or [.incrementProgress] if you want to change the Default MaxValue.
     * @param maxValue The Integral Value to be set as MaxValue for Determinate ProgressBar.
     * @return true if Mode is [.MODE_DETERMINATE] and Progress is set. false otherwise.
     */
    fun setMaxValue(maxValue: Int): Boolean {
        return if (isDeterminate) {
            progressBarDeterminate!!.max = maxValue
            setProgress(progress)
            true
        } else {
            false
        }
    }

    /**
     * Returns the MaxValue of Determinate ProgressBar.
     * Can be used only in [.MODE_DETERMINATE] Mode.
     * @return The Current MaxValue of Determinate ProgressBar if Mode is [.MODE_DETERMINATE]. Else -1 is returned.
     */
    val maxValue: Int
        get() = if (isDeterminate) progressBarDeterminate!!.max else -1

    /**
     * Returns the Progress Value of Determinate ProgressBar.
     * Can be used only in [.MODE_DETERMINATE] Mode.
     * @return The Current Progress Value of Determinate ProgressBar if Mode is [.MODE_DETERMINATE]. Else -1 is returned.
     */
    val progress: Int
        get() = if (isDeterminate) progressBarDeterminate!!.progress else -1

    /**
     * Toggles the Progress TextView's format as Fraction if "true" is passed.
     * Progress TextView's Default format is Percentage format.
     * Can be used only in [.MODE_DETERMINATE].
     * If [.hideProgressText] was used before, this method will again make Progress TextView visible.
     * @param progressTextAsFraction The boolean value to change Progress TextView's format.
     * @return true if Mode is [.MODE_DETERMINATE] and Progress is set. false otherwise and also on Redundant Calls.
     */
    fun showProgressTextAsFraction(progressTextAsFraction: Boolean): Boolean {
        return if (isDeterminate) {
            if (progressTextAsFraction) {
                when (progressViewMode) {
                    SHOW_AS_FRACTION -> return false
                    SHOW_AS_PERCENT, HIDE_PROGRESS_TEXT -> {
                        progressViewMode = SHOW_AS_FRACTION
                        progressTextView!!.text = progressAsFraction
                    }
                }
            } else {
                when (progressViewMode) {
                    SHOW_AS_PERCENT -> return false
                    SHOW_AS_FRACTION, HIDE_PROGRESS_TEXT -> {
                        progressViewMode = SHOW_AS_PERCENT
                        progressTextView!!.text = progressAsPercent
                    }
                }
            }
            true
        } else {
            false
        }
    }

    /**
     * Hides the Progress TextView.
     * Can be used only in [.MODE_DETERMINATE].
     * @return true if Mode is [.MODE_DETERMINATE] and Progress TextView is hidden. false otherwise.
     */
    fun hideProgressText(): Boolean {
        return if (isDeterminate) {
            progressViewMode = HIDE_PROGRESS_TEXT
            if (!isGone(progressTextView)) {
                progressTextView!!.visibility = View.GONE
            }
            true
        } else {
            false
        }
    }

    /**
     * Starts the ProgressDialog and shows it on the Screen.
     */
    fun show() {
        if (autoThemeEnabled) {
            if (isSystemInNightMode && theme == THEME_LIGHT) {
                setThemeInternal(THEME_DARK)
            } else if (!isSystemInNightMode && theme == THEME_DARK) {
                setThemeInternal(THEME_LIGHT)
            }
        }
        progressDialog!!.show()
    }

    /**
     * Dismisses the ProgressDialog, removing it from the Screen.
     * Calls [DialogInterface.OnDismissListener], if it is set using [.setOnDismissListener].
     * To be used after the Task calling ProgressDialog is Over or if any Exception Occurs during Task execution.
     * In case of passing to Another Activity/Fragment, this method SHOULD be called before starting the next Activity/Fragment.
     * Else, it would cause WindowLeakedException.
     */
    fun dismiss() {
        progressDialog!!.dismiss()
    }

    /**
     * Sets the [DialogInterface.OnCancelListener] for ProgressDialog.
     * Should be used only if [.setCancelable] was passed with true earlier since cancel() cannot be called explicitly
     * and ProgressDialog is NOT cancelable by Default.
     * @param onCancelListener [DialogInterface.OnCancelListener] listener object.
     * @return true if ProgressDialog is Cancelable. false otherwise.
     */
    fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener?): Boolean {
        return if (cancelable) {
            progressDialog!!.setOnCancelListener(onCancelListener)
            true
        } else {
            false
        }
    }

    /**
     * Sets the [DialogInterface.OnDismissListener] for ProgressDialog.
     * @param onDismissListener [DialogInterface.OnDismissListener] listener object.
     */
    fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener?) {
        progressDialog!!.setOnDismissListener(onDismissListener)
    }

    /**
     * Sets the [DialogInterface.OnShowListener] for ProgressDialog.
     * @param onShowListener [DialogInterface.OnShowListener] listener object.
     */
    fun setOnShowListener(onShowListener: OnShowListener?) {
        progressDialog!!.setOnShowListener(onShowListener)
    }

    /**
     * Toggles the Cancelable property of ProgressDialog which is false by Default.
     * If it is set to true, the User can cancel the ProgressDialog by pressing Back Button or by touching any other part of the screen.
     * It is NOT RECOMMENDED to set Cancelable to true.
     * @param cancelable boolean value which toggles the Cancelable property of ProgressDialog.
     */
    fun setCancelable(cancelable: Boolean) {
        progressDialog!!.setCancelable(cancelable)
        this.cancelable = cancelable
    }

    /**
     * Checks if ProgressValue is equal to MaxValue.
     * Can be used only in [.MODE_DETERMINATE].
     * @return true if ProgressValue is equal to MaxValue. false otherwise and also if mode is not [.MODE_DETERMINATE].
     */
    val hasProgressReachedMaxValue: Boolean
        get() = if (isDeterminate) progress == maxValue else false

    /**
     * Gets the Integral Value required to reach MaxValue from the current ProgressValue.
     * Can be used only in [.MODE_DETERMINATE].
     * @return The Integral Amount required to reach MaxValue. -1 if mode is not [.MODE_DETERMINATE].
     */
    val remainingProgress: Int
        get() = if (isDeterminate) maxValue - progress else -1

    /**
     * Sets the Secondary ProgressValue.
     * Can be used only in [.MODE_DETERMINATE].
     * @param secondaryProgress The integral value to be set as Secondary ProgressValue.
     * @return true if mode is [.MODE_DETERMINATE] and Secondary ProgressValue is set. false otherwise.
     */
    fun setSecondaryProgress(secondaryProgress: Int): Boolean {
        return if (isDeterminate) {
            progressBarDeterminate!!.secondaryProgress = secondaryProgress
            true
        } else {
            false
        }
    }

    /**
     * Gets the Secondary ProgressValue.
     * Can be used only in [.MODE_DETERMINATE].
     * @return Integral Secondary ProgressValue if mode is [.MODE_DETERMINATE]. -1 otherwise.
     */
    val secondaryProgress: Int
        get() = if (isDeterminate) progressBarDeterminate!!.secondaryProgress else -1

    /**
     * Gets the Integral Value required to reach MaxValue from the current Secondary ProgressValue.
     * Can be used only in [.MODE_DETERMINATE].
     * @return The Integral Amount required to reach MaxValue from Secondary ProgressValue. -1 if mode is not [.MODE_DETERMINATE].
     */
    val secondaryRemainingProgress: Int
        get() = if (isDeterminate) maxValue - secondaryProgress else -1

    /**
     * Checks if Secondary ProgressValue is equal to MaxValue.
     * Can be used only in [.MODE_DETERMINATE].
     * @return true if Secondary ProgressValue is equal to MaxValue. false otherwise and also if mode is not [.MODE_DETERMINATE].
     */
    val hasSecondaryProgressReachedMaxValue: Boolean
        get() = if (isDeterminate) secondaryProgress == maxValue else false

    /**
     * Sets a Custom Drawable to the Indeterminate ProgressBar.
     * Can be used only in [.MODE_DETERMINATE].
     * Use this when you need to define a custom Drawable Design for Indeterminate ProgressBar.
     * Alternative to [.setIndeterminateDrawable].
     * @param progressDrawable The Drawable object used to draw the Indeterminate ProgressBar.
     * @return true if mode is [.MODE_INDETERMINATE] and the Drawable is set. false otherwise.
     * @see .getIndeterminateDrawable
     * @see .setProgressTintList
     */
    fun setIndeterminateDrawable(progressDrawable: Drawable?): Boolean {
        return if (!isDeterminate) {
            progressBarIndeterminate!!.indeterminateDrawable = progressDrawable
            true
        } else {
            false
        }
    }

    /**
     * Sets a Custom Drawable from the passed Drawable resource to the Indeterminate ProgressBar.
     * Can be used only in [.MODE_INDETERMINATE].
     * Use this when you need to define a custom Drawable Design for Indeterminate ProgressBar.
     * Alternative to [.setIndeterminateDrawable].
     * @param progressDrawableResID The resource id of the Drawable resource used to draw the Indeterminate ProgressBar.
     * @return true if mode is [.MODE_INDETERMINATE] and the Drawable is set. false otherwise.
     * @see .getIndeterminateDrawable
     * @see .setProgressTintList
     */
    fun setIndeterminateDrawable(@DrawableRes progressDrawableResID: Int): Boolean {
        return setIndeterminateDrawable(ContextCompat.getDrawable(context, progressDrawableResID))
    }

    /**
     * Gets the Drawable object used to draw the Indeterminate ProgressBar.
     * Can be used only in [.MODE_INDETERMINATE].
     * @return Drawable Object if mode is [.MODE_INDETERMINATE]. null otherwise.
     * @see .setIndeterminateDrawable
     * @see .getProgressTintList
     */
    val indeterminateDrawable: Drawable?
        get() = if (!isDeterminate) progressBarIndeterminate!!.indeterminateDrawable else null

    /**
     * Sets a Custom Drawable to the Determinate ProgressBar.
     * Can be used only in [.MODE_DETERMINATE].
     * Use this when you need to define a custom Drawable Design for Determinate ProgressBar.
     * Alternative to [.setDeterminateDrawable].
     * @param progressDrawable The Drawable object used to draw the Determinate ProgressBar.
     * @return true if mode is [.MODE_DETERMINATE] and the Drawable is set. false otherwise.
     * @see .getDeterminateDrawable
     * @see .setProgressTintList
     */
    fun setDeterminateDrawable(progressDrawable: Drawable?): Boolean {
        return if (isDeterminate) {
            progressBarDeterminate!!.progressDrawable = progressDrawable
            true
        } else {
            false
        }
    }

    /**
     * Sets a Custom Drawable from the passed Drawable resource to the Determinate ProgressBar.
     * Can be used only in [.MODE_DETERMINATE].
     * Use this when you need to define a custom Drawable Design for Determinate ProgressBar.
     * Alternative to [.setDeterminateDrawable].
     * @param progressDrawableResID The resource id of the Drawable resource used to draw the Determinate ProgressBar.
     * @return true if mode is [.MODE_DETERMINATE] and the Drawable is set. false otherwise.
     * @see .getDeterminateDrawable
     * @see .setProgressTintList
     */
    fun setDeterminateDrawable(@DrawableRes progressDrawableResID: Int): Boolean {
        return setDeterminateDrawable(ContextCompat.getDrawable(context, progressDrawableResID))
    }

    /**
     * Gets the Drawable object used to draw the Determinate ProgressBar.
     * Can be used only in [.MODE_DETERMINATE].
     * @return Drawable Object if mode is [.MODE_DETERMINATE]. null otherwise.
     * @see .setDeterminateDrawable
     * @see .getProgressTintList
     */
    val determinateDrawable: Drawable?
        get() = if (isDeterminate) progressBarDeterminate!!.progressDrawable else null
    /**
     * Returns the tint applied to Indeterminate Drawable if mode is [.MODE_INDETERMINATE].
     * Returns the tint applied to Determinate Drawable if mode is [.MODE_DETERMINATE].
     * @return ColorStateList object specifying the tint applied to ProgressBar's Drawable.
     * @see .setProgressTintList
     */
    /**
     * Applies a tint to Indeterminate Drawable if mode is [.MODE_INDETERMINATE].
     * Applies a tint to Determinate Drawable if mode is [.MODE_DETERMINATE].
     * @param tintList The ColorStateList object used to apply tint to ProgressBar's Drawable.
     * @see .getProgressTintList
     */
    var progressTintList: ColorStateList?
        get() = if (isDeterminate) progressBarDeterminate!!.progressTintList else progressBarIndeterminate!!.indeterminateTintList
        set(tintList) {
            if (!isDeterminate) progressBarIndeterminate!!.indeterminateTintList =
                tintList else progressBarDeterminate!!.progressTintList =
                tintList
        }

    /**
     * Sets the NegativeButton with the passed text for the ProgressDialog and also sets the OnClickListener for the Button.
     * NegativeButton is hidden by default. This method makes it visible.
     * Default Text for NegativeButton is "CANCEL". This can be used by passing null for text parameter.
     * This method also enables the Title to be shown (even if it was hidden till then). If null is passed, default title "ProgressDialog" will be used.
     * If [.setTitle] or [.setTitle] was used before, and new Title is passed, the new Title will Override the previously set Title.
     * If null is passed for listener, default listener which dismisses the ProgressDialog when clicked, will be used.
     * Alternative to [.setNegativeButton].
     * @param text The text to be set in the NegativeButton. If null, "CANCEL" will be set.
     * @param listener The [View.OnClickListener] listener to be set to NegativeButton.
     */
    fun setNegativeButton(
        text: CharSequence?,
        title: CharSequence?,
        listener: View.OnClickListener?
    ) {
        if (text != null) negativeButton!!.text = text
        negativeButton!!.setOnClickListener(listener
            ?: View.OnClickListener { v: View? -> dismiss() })
        if (isGone(negativeButton)) {
            enableNegativeButton(title)
        }
    }

    /**
     * Sets the NegativeButton with the text from passed resource id for the ProgressDialog and also sets the OnClickListener for the Button.
     * NegativeButton is hidden by default. This method makes it visible.
     * Default Text for NegativeButton is "CANCEL". To use default text, use [.setNegativeButton].
     * This method also enables the Title to be shown (even if it was hidden till then). If invalid titleResID is passed, default title "ProgressDialog" will be used.
     * If [.setTitle] or [.setTitle] was used before, and new titleResID is passed, the new Title will Override the previously set Title.
     * If null is passed for listener, default listener which dismisses the ProgressDialog when clicked, will be used.
     * @param textResID The resource id of the text to be set in the NegativeButton.
     * @param listener The [View.OnClickListener] listener to be set to NegativeButton.
     */
    fun setNegativeButton(
        @StringRes textResID: Int,
        @StringRes titleResID: Int,
        listener: View.OnClickListener?
    ) {
        setNegativeButton(
            context.getText(textResID).toString(),
            context.getText(titleResID).toString(),
            listener
        )
    }

    /**
     * Hides the NegativeButton. NegativeButton is Hidden by Default.
     * Use this method only if you have used [.setNegativeButton] or
     * [.setNegativeButton] before.
     * Note : This method will not hide the Title. You have to explicitly call [.hideTitle] to do the same.
     */
    fun hideNegativeButton() {
        if (!isGone(negativeButton)) {
            negativeButton!!.visibility = View.GONE
        }
    }

    private val progressAsFraction: String
        private get() = "$progress/$maxValue"
    private val progressAsPercent: String
        private get() {
            val `val` = progress.toDouble() / maxValue.toDouble() * 100
            return String.format(Locale.getDefault(), "%.2f", `val`) + "%"
        }

    private fun setProgressText() {
        when (progressViewMode) {
            SHOW_AS_FRACTION -> progressTextView!!.text =
                progressAsFraction
            SHOW_AS_PERCENT -> progressTextView!!.text = progressAsPercent
            HIDE_PROGRESS_TEXT -> {}
        }
    }

    private fun isVisible(view: View?): Boolean {
        return view!!.visibility == View.VISIBLE
    }

    private fun isGone(view: View?): Boolean {
        return view!!.visibility == View.GONE
    }

    private val isDeterminate: Boolean
        private get() = mode == MODE_DETERMINATE

    @get:RequiresApi(api = Build.VERSION_CODES.R)
    private val isSystemInNightMode: Boolean
        private get() = context.resources.configuration.isNightModeActive
    private val isAboveOrEqualToAnd11: Boolean
        private get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

    private fun enableNegativeButton(title: CharSequence?) {
        negativeButton!!.visibility = View.VISIBLE
        if (isGone(titleView)) setTitle(title)
    }

    private fun setThemeInternal(@ThemeConstant themeConstant: Int): Boolean {
        return when (themeConstant) {
            THEME_DARK -> {
                dialogLayout!!.background =
                    ContextCompat.getDrawable(context, R.drawable.bg_dialog_dark)
                titleView!!.setTextColor(ContextCompat.getColor(context, R.color.white))
                textViewIndeterminate!!.setTextColor(ContextCompat.getColor(context, R.color.white))
                textViewDeterminate!!.setTextColor(ContextCompat.getColor(context, R.color.white))
                progressTextView!!.setTextColor(ContextCompat.getColor(context, R.color.white_dark))
                negativeButton!!.setTextColor(ContextCompat.getColor(context, R.color.white))
                theme = themeConstant
                true
            }
            THEME_LIGHT -> {
                dialogLayout!!.background =
                    ContextCompat.getDrawable(context, R.drawable.bg_dialog)
                titleView!!.setTextColor(ContextCompat.getColor(context, R.color.black))
                textViewIndeterminate!!.setTextColor(ContextCompat.getColor(context, R.color.black))
                textViewDeterminate!!.setTextColor(ContextCompat.getColor(context, R.color.black))
                progressTextView!!.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.black_light
                    )
                )
                negativeButton!!.setTextColor(ContextCompat.getColor(context, R.color.black))
                theme = themeConstant
                true
            }
            else -> false
        }
    }

    companion object {
        /**
         * The default Theme for ProgressDialog (even if it is not passed in Constructor).
         * Suitable for apps having a Light Theme.
         * Theme can be changed later using [.setTheme].
         */
        const val THEME_LIGHT = 1

        /**
         * This theme is suitable for apps having a Dark Theme.
         * This Constant SHOULD be passed explicitly in the Constructor for setting Dark Theme for ProgressDialog.
         * Theme can be changed later using [.setTheme].
         */
        const val THEME_DARK = 2

        /**
         * When this ThemeConstant is used, ProgressDialog's theme is automatically changed to match the System's theme each time before [.show] is called.
         * This Constant can be used starting from Android API Level 31 (Android 11) ONLY.
         * [.setTheme] will throw [IllegalArgumentException] if this Constant is passed in method call in Android versions lower than Android 11.
         */
        @RequiresApi(api = Build.VERSION_CODES.R)
        const val THEME_FOLLOW_SYSTEM = -1

        /**
         * The default mode for ProgressDialog where an Indeterminate Spinner is shown for indicating Progress (even if it is not passed in Constructor).
         * Suitable for implementations where the exact progress of an operation is unknown to the Developer.
         */
        const val MODE_INDETERMINATE = 3

        /**
         * In this mode, a Determinate ProgressBar is shown inside the ProgressDialog for indicating Progress.
         * It also has a TextView for numerically showing the Progress Value either as Percentage or as Fraction.
         * Progress Value is shown as Percentage by Default which can be changed using [.showProgressTextAsFraction];
         */
        const val MODE_DETERMINATE = 4
        private const val SHOW_AS_FRACTION = 5
        private const val SHOW_AS_PERCENT = 6
        private const val HIDE_PROGRESS_TEXT = 7
    }
}