package com.swyft.app.helper

import android.annotation.SuppressLint
import android.app.*
import android.app.PendingIntent.getActivity
import android.content.*
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.decodeFile
import android.graphics.Color
import android.graphics.Typeface
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Environment
import android.preference.PreferenceManager
import android.provider.Settings
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.SuperscriptSpan
import android.util.DisplayMetrics
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.webkit.WebView
import android.widget.*
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.pranav.productbooking.helper.Debug
import com.example.quickquiz.R
import java.io.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

object Utils {

   /* fun addFragmentToActivity(manager: FragmentManager, fragment: Fragment, frameId: Int = 0) {

        val transaction = manager.beginTransaction()
        Debug.e("name", fragment.javaClass.simpleName)
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
        transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.add(frameId, fragment)
        transaction.commit()

    }

    fun replaceFragmentToActivity(manager: FragmentManager, fragment: Fragment, frameId: Int = 0) {

        val transaction = manager.beginTransaction()
        Debug.e("name", fragment.javaClass.simpleName)

        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
        transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.replace(frameId, fragment, fragment.javaClass.simpleName)
        transaction.commit()

    }*/

    fun setTimeFormat(time: String): String{
        val df= SimpleDateFormat("HH:mm")
        val date:Date = df.parse(time)
        return SimpleDateFormat("hh:mm a").format(date)
    }

//    fun showAlertDialog(context: Activity, text: String) {
//        val dialog = MaterialDialog.Builder(context)
//                .customView(R.layout.dialog_error, false)
//                .show()
//
//        Utils.setWindowManagerCustom(dialog)
//
//        val lblMessage: TextView = dialog.findViewById(R.id.lblMessage) as TextView
//        val btnOk: Button = dialog.findViewById(R.id.btnOk) as Button
//
//        lblMessage.text = text
//
//        btnOk.setOnClickListener { dialog.dismiss() }
//
//
//        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//    }


    /* fun showSuccessDialog(activity: Activity, text: String = "") {
         val dialog = MaterialDialog.Builder(activity)
                 .customView(R.layout.dialog_success, false)
                 .show()

         Utils.setWindowManagerCustom(dialog)

         dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

     }
 */

    /*fun loadImageUsingGlide(context: Context, imgUrl: Any?, imageView: ImageView, placeholder: Int) {
        Glide.with(context)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(placeholder)
                .placeholder(placeholder)
                .dontTransform()
                .override(Constant.PREFERRED_IMAGE_SIZE_PREVIEW, Constant.PREFERRED_IMAGE_SIZE_FULL)
                .into(imageView)
    }*/

    fun getColorWrapper(context: Context, id: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getColor(id)
        } else {
            context.resources.getColor(id)
        }
    }

    fun IsValidEmailAddress(hex: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(EMAIL_PATTERN)
        matcher = pattern.matcher(hex)
        return matcher.matches()
    }
    /*fun isInternetConnected(activity: Context?): Boolean {
        activity?.let {

            return if (isInternetConnectedCheck(activity)) {

                !this.isThetaConnected(activity)

            } else {
                Utils.showToast(activity, activity.getString(R.string.internet_error))
                false
            }
        }


        return true
    }*/


    /* fun isMobileDataConnected(context: Context): Boolean {

         return if (isInternetConnected(context))
             MobileDataChek.isMobileDataConnected(context)
         else false

     }*/

    fun getHashKey(activity: Activity) {
        try {
            val info = activity.packageManager.getPackageInfo(activity.packageName,
                    PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KeyHash:", android.util.Base64.encodeToString(md.digest(), android.util.Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

    }

    private fun isInternetConnectedCheck(mContext: Context?): Boolean {
        var outcome = false

        try {
            if (mContext != null) {
                val cm = mContext
                        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                val networkInfos = cm.allNetworkInfo

                for (tempNetworkInfo in networkInfos) {

                    if (tempNetworkInfo.isConnected) {
                        outcome = true
                        break
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return outcome
    }

    fun isWifiConnected(context: Context): Boolean {
        var connManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager;
        var mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return mWifi?.isConnected!!
    }

/*

    fun chnageLanguage(context: Activity, language: String) {
        val res = context.resources
// Change locale settings in the app.
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(Locale(language.toLowerCase())) // API 17+ only.
// Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm)
    }
*/


    fun writeToFile(data: String, context: Context) {

        val outputStreamWriter: OutputStreamWriter = OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE))
        outputStreamWriter.write(data)
        outputStreamWriter.close()
    }

    /* fun generateNoteOnSD(context: Context, sBody: String) = try {
         val root = File(Environment.getExternalStorageDirectory(), context.getString(R.string.app_name))


         val gpxfile = File(root, "Logs.txt")
         if (!gpxfile.exists()) {
             root.mkdirs()
             val writer = FileWriter(gpxfile)
             writer.append("\n" + sBody)
             writer.flush()
             writer.close()
         } else {
             getLogFile(context, "\n" + sBody)
         }


     } catch (e: IOException) {
         e.printStackTrace()
     }*/

    /* fun getLogFile(context: Context, msg: String) {
         val root = File(Environment.getExternalStorageDirectory(), context.getString(R.string.app_name))
         try {
             val gpxfile = File(root, "Logs.txt")
             FileUtil.appendStringToFile(msg, gpxfile)
         } catch (e: Exception) {
 //            e.message?.let { showToast(context, it) }
         }

     }

 */
    /*fun getOutputMediaFileUri(activity: Activity): Uri {

        return FileProvider.getUriForFile(activity,
                BuildConfig.APPLICATION_ID + ".provider",
                getOutputMediaFile(activity)!!)

    }*/

    fun getOutputMediaFile(activity: Activity): File? {

        // External sdcard location
        val mediaStorageDir = File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "." + activity.getString(R.string.app_name))

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }

        // Create a media file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale("en")).format(Date())
        val mediaFile: File
        mediaFile = File(mediaStorageDir.path + File.separator
                + "IMG_" + timeStamp + ".jpg")

        return mediaFile
    }


    fun initHomeFrag(manager: FragmentManager, fragment: Fragment, frameId: Int = 0) {

        val transaction = manager.beginTransaction()
        transaction.replace(frameId, fragment, fragment::class.java.simpleName)
        transaction.commit()

    }

    /*fun checkForThetaConnectionDialogue(context: Context): Boolean {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ssid = wifiInfo.ssid
//        ssid.toLowerCase().indexOf("theta") != -1
        return ssid.toLowerCase().contains("theta")

    }*/

    fun setAlarm(context: Context, ALARM_ACTION: String, time: Long) {
        val am = context.applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(ALARM_ACTION)
        val pi = PendingIntent.getBroadcast(context.applicationContext, 0, i, 0)

        val cal = Calendar.getInstance()
        cal.timeInMillis = time
        Log.e("time", time.toString() + "")
        am.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pi)
    }

    fun canceAlarm(context: Context, ALARM_ACTION: String) {
        val am = context.applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(ALARM_ACTION)
        val pi = PendingIntent.getBroadcast(context.applicationContext, 0, i, 0)
        am.cancel(pi)
    }

    fun checkExtension(url: String): String {

        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        Log.e("MiMetype", extension)
        return extension
    }

    fun showToast(context: Context, text: String?) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    /*   fun showUnauthorizedError(context: Context) =
               Toast.makeText(context, context.getString(R.string.unauthorized), Toast.LENGTH_SHORT).show()

       fun showBadRequestError(context: Context) =
               Toast.makeText(context, context.getString(R.string.bad_request), Toast.LENGTH_SHORT).show()

       fun showError(context: Context) =
               Toast.makeText(context, context.getString(R.string.err_unknown), Toast.LENGTH_SHORT).show()*/


    fun getDeviceWidth(context: Activity): Int {
        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        return width
    }

    fun getDeviceHeight(context: Activity): Int {
        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        return height
    }

    fun isLocationEnabled(context: Context): Boolean {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gps_enabled = false
        var network_enabled = false

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
        }

        return gps_enabled || network_enabled
    }

    fun convertDpToPixel(dp: Float): Int {
        val metrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return Math.round(px)
    }

    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver,
                Settings.Secure.ANDROID_ID);
    }

    fun getVersionName(context: Context): String {

        var versionName = context.packageManager
                .getPackageInfo(context.packageName, 0).versionName;

        return versionName
    }

    fun getSecret(email: String, password: String): String {

        val text = email.reversed() + password.reversed()

        return md5(text)
    }

    fun isEmailValid(email: String): Boolean {

        val isEmailValid = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()

        Log.e("isEmailValid", isEmailValid.toString())

        return isEmailValid

    }

    fun md5(s: String): String {
        val MD5 = "MD5"
        try {
            // Create MD5 Hash
            val digest = java.security.MessageDigest
                    .getInstance(MD5)
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2)
                    h = "0" + h
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }


    fun isStringValid(text: String?): Boolean {

        return !(text == null || text.isEmpty() || text == "null")
    }


    @Throws(IOException::class)
    fun getSavedImagePicturePath(context: Context, bitmap: Bitmap): String {

        val root = Environment.getExternalStorageDirectory().toString()
        val mydir = File(root + "/" + context.getString(R.string.app_name))
        mydir.mkdirs()

        val fname = "Image-" + Calendar.getInstance().timeInMillis + ".png"
        val filepath = root + "/" + context.getString(R.string.app_name) + "/" + fname


        val file = File(mydir, fname)
        if (file.exists()) file.delete()


        val out = FileOutputStream(file)

        bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)
        out.flush()
        out.close()

        return filepath

    }

//    this function will create dynamic temUri

    @Throws(Exception::class)
    fun createTemporaryFile(context: Context): File {
        var tempDir = Environment.getExternalStorageDirectory()


        tempDir = File(tempDir.absolutePath + "/." + context.getString(R.string.app_name))
        if (!tempDir.exists()) {
            tempDir.mkdirs()
        }
        return File.createTempFile("Image-", ".png", tempDir)
    }

    /* fun grabImage(baseContext: Activity, mImageUri: Uri, imageView: ImageView): Bitmap? {
 //        baseContext.contentResolver.notifyChange(mImageUri, null)
 //        val cr = baseContext.contentResolver
 //        val bitmap: Bitmap
 //        try {
 //            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri)
 //            imageView.setImageBitmap(bitmap)
 //        } catch (e: Exception) {
 //            Utils.showToast(baseContext, "Failed to load")
 //        }


         var capture_path_name = mImageUri.toString()
         if (capture_path_name.length == 0) {
             return null
         }

         capture_path_name = capture_path_name.substring(capture_path_name.lastIndexOf("/") + 1, capture_path_name.length)

         val file_capture = File(File(
                 Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "." + baseContext.getString(R.string.app_name)), capture_path_name)

         var bitmap: Bitmap? = null

         if (file_capture.exists()) {

             bitmap = ImageUtils.getCompressedBitmap(baseContext, file_capture.absolutePath)
             imageView.setImageBitmap(bitmap)
         }

         return bitmap


     }*/

    fun showAlert(c: Context, msString: String) {
        AlertDialog.Builder(c)
                .setMessage(msString)
                .setPositiveButton(android.R.string.ok) { dialog, which -> }
                .show()
    }

    /*  fun sendCamaraIntent(baseContext: Activity, imageCaptureCode: Int): Uri {

          var photo: File? = null

          val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

          try {
              // place where to store camera taken picture
              photo = createTemporaryFile(baseContext)
              photo?.delete()
          } catch (e: Exception) {
              showToast(baseContext, baseContext.getString(R.string.alert_check_sd_card))

          }

          var mImageUri = Uri.fromFile(photo)
          intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri)

          baseContext.startActivityForResult(intent, imageCaptureCode)

          return mImageUri
      }
  */
    fun getThumbnil(file: File): Bitmap {
        val bitmap = BitmapFactory.decodeFile(file.absolutePath, null)
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inJustDecodeBounds = true // obtain the size of the image, without loading it in memory
        decodeFile(file.absolutePath, bitmapOptions)
        val desiredWidth = bitmap.width / 2
        val desiredHeight = bitmap.height / 2
        val widthScale = bitmapOptions.outWidth.toFloat() / desiredWidth
        val heightScale = bitmapOptions.outHeight.toFloat() / desiredHeight
        val scale = Math.min(widthScale, heightScale)
        var sampleSize = 1
        while (sampleSize < scale) {
            sampleSize *= 2
        }
        bitmapOptions.inSampleSize = sampleSize // this value must be a power of 2,
        bitmapOptions.inJustDecodeBounds = false // now we want to load the image
        bitmap.recycle()
        return decodeFile(file.absolutePath, bitmapOptions)
    }

    fun getLocaleDateFromServer(date: String): String {
        val utcToCurrentTime: Date = parseTimeUTCtoDefault(date, "yyyy-MM-dd HH:mm:ss")
        return parseTime(utcToCurrentTime.time, "yyyy-MM-dd HH:mm:ss")
    }

    fun getTimeDifferenceInLong(pDate: String): Long {

        val capitalUTCDate = getLocaleDateFromServer("" + pDate)
        val givenDate = parseTime(capitalUTCDate, "yyyy-MM-dd HH:mm:ss")

        val localDate = parseTime(parseTime(Date().time, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss")

        val diff = givenDate.time - localDate.time

        Log.e("diffs", "=================================")
        Log.e("diffs", diff.toString())
        Log.e("given", givenDate.time.toString())
        Log.e("current", localDate.time.toString())
        Log.e("diffs", "=================================")
        return diff

    }

    private fun parseTimeUTCtoDefault(time: String, pattern: String): Date {

        var sdf = SimpleDateFormat(pattern,
                Locale("en"))

        try {
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            var d = sdf.parse(time)
            sdf = SimpleDateFormat(pattern, Locale("en"))
            sdf.timeZone = Calendar.getInstance().timeZone;
            return sdf.parse(sdf.format(d))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return Date()
    }

    fun parseTime(time: Long, pattern: String): String {
        val sdf = SimpleDateFormat(pattern,
                Locale("en"))
        return sdf.format(Date(time))
    }

    fun parseTime(time: String, fromPattern: String,
                  toPattern: String): String {
        var sdf = SimpleDateFormat(fromPattern,
                Locale("en"))
        try {
            val d = sdf.parse(time)
            sdf = SimpleDateFormat(toPattern, Locale("en"))
            return sdf.format(d)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getTimeDifference(pDate: String): String {
        var diffInDays = 0
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        format.timeZone = TimeZone.getTimeZone("GMT")
        val c = Calendar.getInstance()
        val formattedDate = format.format(c.time)

        var d1: Date? = null
        var d2: Date? = null
        try {

            d1 = format.parse(formattedDate)
            d2 = format.parse(pDate)
            val diff = d1!!.time - d2!!.time

            diffInDays = (diff / (1000 * 60 * 60 * 24)).toInt()
            if (diffInDays > 0) {
                if (diffInDays == 1) {
                    return "Ago $diffInDays day"
                } else {
                    val week = diffInDays / 7
                    return if (week > 0) {
                        if (week == 1)
                            "Ago $week week"
                        else
                            "Ago $week weeks"

                    } else
                        "Ago $diffInDays days"
                }
            } else {
                val diffHours = (diff / (60 * 60 * 1000)).toInt()
                if (diffHours > 0) {
                    return if (diffHours == 1) {
                        "Ago $diffHours hour"
                    } else {
                        "Ago $diffHours hours"
                    }
                } else {
                    val diffMinutes = (diff / (60 * 1000) % 60).toInt()
                    return if (diffMinutes == 0) {
                        "Right now"
                    } else if (diffMinutes == 1) {
                        "Ago $diffMinutes minute"
                    } else {
                        "Ago $diffMinutes minutes"
                    }
                }
            }

        } catch (e: ParseException) {
            return ""
        }

    }

    fun getFormettedDate(context: Context, strDate: String, defaultDateFormet: String, newDateFormet: String): String {
        try {
            val locale = Locale("en")
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            val originalFormat = SimpleDateFormat(defaultDateFormet, Locale.getDefault())
            val targetFormat = SimpleDateFormat(newDateFormet)
            val date = originalFormat.parse(strDate)
            return targetFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return strDate
    }

    fun getFormettedDateSpanish(context: Context, strDate: String?, defaultDateFormet: String, newDateFormet: String): String {

        var date_obj: Date? = null
        val fmt = SimpleDateFormat(defaultDateFormet)

        var finaldate = ""
        try {
            date_obj = fmt.parse(strDate)
            val destDf = SimpleDateFormat(newDateFormet, Locale("es"))
            finaldate = destDf.format(date_obj)
//            finaldate = finaldate.replace("AM", "am").replace("PM", "pm")
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return finaldate
    }

    fun convertDateFirstLaterCaps(context: Context, date: String, defaultDateFormet: String, newDateFormet: String): CharSequence? {
        var ACaps = ""

        val date = getFormettedDateSpanish(context, date, defaultDateFormet,
                newDateFormet)
        if (date.isNotEmpty()) {
            ACaps = date.toUpperCase()[0] + date.substring(1, date.length)
        }


        return ACaps
    }

    fun hideSoftKeyboard(activity: Activity) {


        try {
            val view = activity.currentFocus
            if (view != null) {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        } catch (e: Exception) {
        }

    }

    fun hideKeyboardonWhitespace(viewgroup: ViewGroup, activity: Activity){
        var view : View
        if (viewgroup is ViewGroup) {
            for (i in 0..viewgroup.childCount) {
                if (viewgroup.getChildAt(i) != null) {
                    view = viewgroup.getChildAt(i)
                    if(view !is EditText){
                        view.setOnTouchListener(object : View.OnTouchListener {
                            override fun onTouch(v: View, m: MotionEvent): Boolean {
                                Utils.hideSoftKeyboard(activity)
                                return false
                            }
                        })
                    }
                }
            }
        }
    }


    fun showKeyboard(activity: Activity, editText: EditText?) {
        val imm: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
        imm.toggleSoftInputFromWindow(
                editText?.applicationWindowToken,
                InputMethodManager.SHOW_FORCED, 0)
    }

    /*   fun loadImageUsingGlide(context: Context, imgUrl: Any?, imageView: ImageView, placeholder: Int) {
           Glide.with(context)
                   .load(imgUrl)
                   .diskCacheStrategy(DiskCacheStrategy.ALL)
                   .error(placeholder)
                   .placeholder(placeholder)
                   .dontTransform()
                   .override(Constant.PREFERRED_IMAGE_SIZE_PREVIEW, Constant.PREFERRED_IMAGE_SIZE_FULL)
                   .into(imageView)
       }
   */

    fun loadDataInWebView(newsDescription: String, webView: WebView) {

        val style = "<head><style type=\"text/css\">\n" +
                "@font-face {\n" +
                "    font-family: MyFont;\n" +
                "    src: url(\"file:///android_asset/fonts/Muli-Light.ttf\")\n" +
                "}\n" +
                "body {\n" +
                "    font-family: MyFont;\n" +
                "    font-size: medium;\n" +
                "    text-align: justify;\n" +
                "}\n" +
                "img { width : 100% ;}" +
                "</style></head>"


        val detailWebViewText = "<html>$style<body>" + newsDescription
                .replace("width=", "width = \"100%\"  dummy1 =").replace("height=", "dummy2=") + "</body></Html>"

        val detailWebViewText1 = removeSquareBrackets(detailWebViewText)


        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.loadDataWithBaseURL("file:///android_asset/", detailWebViewText1, "text/html", "UTF-8", "")

    }

    private fun removeSquareBrackets(s: String): String {

        val startSquareBracketIndex: Int = s.indexOf("[")

        return if (startSquareBracketIndex == -1) {
            s
        } else {
            val text = s.substring(s.indexOf("["), s.indexOf("]") + 1)
            removeSquareBrackets(s.replace("" + text, ""))
        }
    }


//    fun showCustomTab(activity: Activity, url: String) {
//
//        var surlUpdate: String = ""
//        surlUpdate = url;
//
//        if (surlUpdate.isEmpty()) {
//            return
//        }
//
//        if (!surlUpdate.contains("http")) {
//            surlUpdate = "https:/$url"
//        }
//
//
//        val color = activity.resources.getColor(R.color.colorPrimary)
//        val secondaryColor = activity.resources.getColor(R.color.colorPrimaryDark)
//
//        val builder = CustomTabsIntent.Builder()
//        builder.setToolbarColor(color)
//        builder.setSecondaryToolbarColor(secondaryColor)
//        builder.setStartAnimations(activity, R.anim.slide_in_left, R.anim.slide_out_left)
//        builder.setExitAnimations(activity, R.anim.slide_in_right, R.anim.slide_out_right)
//
//        val customTabsIntent = builder.build()
//        customTabsIntent.launchUrl(activity, Uri.parse(surlUpdate))
//
//    }

//    text?.let { detailWebViewText.replace(text, "") }


    fun appInstalledOrNot(context: Context, uri: String): Boolean {
        val pm = context.packageManager
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }

        return false
    }


    fun random(min: Int, max: Int): Int {

        val value = Math
                .round((min + Math.random() * (max - min + 1)).toFloat())

        return when {
            value > max -> max
            value < min -> min
            else -> value
        }

    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(): String {
        var c = Calendar.getInstance().time
        System.out.println("Current time => $c")

        var df = SimpleDateFormat("dd/MM/yy")
        var formattedDate = df.format(c)
        return formattedDate
    }

    private fun getNotificationIcon(): Int {
        val whiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        return if (whiteIcon) R.mipmap.ic_launcher else R.mipmap.ic_launcher
    }

    fun setDecimalPointLimit(price: Double): String {
        val formatter = NumberFormat.getNumberInstance()
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        return formatter.format(price)
    }

    fun setPowerText(context: Context, textView: TextView, price: String, textSize: Int) {
        try {
            if (!price.contains(".")) {
                "$price.00"
            }

            var tmpActualPrice: Double = price.toDouble()

            run {
                textView.text = "Q" + setDecimalPointLimit(tmpActualPrice).toString()
                //val textSize1 = context.resources.getDimensionPixelSize(R.dimen.very_small_text)
                val textSize1 = context.resources.getDimensionPixelSize(textSize)
                val s = textView.text.toString().trim { it <= ' ' }
                val ss1 = SpannableString(s)
                ss1.setSpan(SuperscriptSpan(), s.indexOf(".") + 1, s.length, 0) // set size
                ss1.setSpan(AbsoluteSizeSpan(textSize1), s.indexOf(".") + 1, s.length, 0)
                textView.text = ss1
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun parseTime(time: String, pattern: String): Date {
        val sdf = SimpleDateFormat(pattern,
                Locale("en"))
        try {
            return sdf.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return Date()
    }

    fun setPref(c: Context, pref: String, `val`: String) {
        val e = PreferenceManager.getDefaultSharedPreferences(c).edit()
        e.putString(pref, `val`)
        e.commit()
    }

    fun getPref(c: Context, pref: String, `val`: String): String? {
        return PreferenceManager.getDefaultSharedPreferences(c).getString(pref,
                `val`)
    }


    fun ShowToast(c: Activity, Message: Int) {
        Toast.makeText(c, Message, Toast.LENGTH_LONG).show()
    }

    fun ShowToast(c: Context, Message: String) {
        Toast.makeText(c, Message, Toast.LENGTH_LONG).show()
    }

    fun buttonAnimation(c: Context, animation: Int, textView: View) {
        var a: Animation = AnimationUtils.loadAnimation(c, animation)
        a.reset()
        textView.clearAnimation()
        textView.startAnimation(a)
    }

    fun getEmoji(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

/* fun isWifiOrMobileDataConnected(context: Context): Boolean {
     if (isInternetConnectedCheck(context)) {
         val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
         val wifi = connMgr?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
         val mobile = connMgr?.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

         val wifiManager: WifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
         val info: WifiInfo = wifiManager.connectionInfo
         val ssid = info.ssid

         if (wifi?.isConnected == true && (!ssid.toLowerCase().contains("theta") && wifi.extraInfo?.toLowerCase()?.contains("theta") == false)) {
             return true
         } else if (mobile?.isConnected == true) {
             return true
         }
     }
     return false
 }*/


    /*fun isThetaConnected(activity: Context): Boolean {
        val connMgr = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val wifi = connMgr?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile = connMgr?.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val wifiManager: WifiManager = activity.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info: WifiInfo = wifiManager.connectionInfo
        val ssid = info.ssid

        return ssid.toLowerCase().contains("theta") || wifi?.extraInfo?.toLowerCase()?.contains("theta") == true

    }*/

    /*@SuppressLint("StaticFieldLeak")
    var dialog: MaterialDialog? = null
*/

    fun isServiceRunningInBackground(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    fun isServiceRunningInForeground(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                if (service.foreground) {
                    return true
                }
            }
        }
        return false
    }


    fun isValidPassword(password: String): Boolean {

        val pattern: Pattern
        val matcher: Matcher

        // 1 lawer
        // 1 caps
        // 1 number
        // min 8 max 20 length

        val PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})"

        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)

        return matcher.matches()

    }

    fun saveImageInApp(activity: Activity, bitmap: Bitmap) {
        val cw = ContextWrapper(activity)
        val directory = cw.getDir("profile", Context.MODE_PRIVATE)
        if (!directory.exists()) {
            directory.mkdir()
        }
        val mypath = File(directory, "thumbnail.png")

        var fos: FileOutputStream? = null
        fos = FileOutputStream(mypath)
//    resizedbitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.close()
    }


//    fun saveImage(activity: Activity, finalBitmap: Bitmap): String {
//        val root = Environment.getExternalStorageDirectory().toString()
//        val myDir = File("$root/" + activity.getString(R.string.app_name))
//        myDir.mkdirs()
//        val fname = "Image-" + Calendar.getInstance().timeInMillis + ".jpg"
//
//        val file = File(myDir, fname)
//        if (file.exists()) file.delete()
//        try {
//            val out = FileOutputStream(file)
//            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
//            out.flush()
//            out.close()
//
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return file.absolutePath
//    }

    fun yoyoAnimate(view: View) {
        YoYo.with(Techniques.Shake)
                .duration(700)
                .playOn(view)
    }

    fun isDataNotEmpty(editText: EditText? = null, textView: TextView? = null): Boolean {

        var isValidate: Boolean = false

        editText?.let {
            isValidate = editText.text.takeIf { isStringValid(it.toString()) }?.let { true } ?: let { false }
        } ?: let {
            isValidate = textView?.text.takeIf { isStringValid(it.toString()) }?.let { true } ?: let { false }
        }

        return isValidate

    }

    fun saveImage(activity: Activity, finalBitmap: Bitmap, fileId: String?): String {
        val cw = ContextWrapper(activity)
        val directory = cw.getDir("profile", Context.MODE_PRIVATE)

        if (!directory.exists()) {
            directory.mkdir()
        }

        directory.mkdirs()

        val uniqueFileId: String = if (fileId?.isNotEmpty() == true) {
            Calendar.getInstance().timeInMillis.toString() + "_" + fileId.substring(fileId.lastIndexOf("/") + 1, fileId.length)
        } else {
            "IMG_" + Calendar.getInstance().timeInMillis + ".JPG"
        }

        val file = File(directory, uniqueFileId)

        if (file.exists()) file.delete()

        try {
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()


        } catch (e: Exception) {
            e.printStackTrace()
        }

        return file.absolutePath
    }


    fun getBitmapFromPath(path: String): Bitmap? {
        val bmOptions = BitmapFactory.Options()
        return BitmapFactory.decodeFile(path, bmOptions)
    }

    fun checkIfImageFitPanorama(path: String): Boolean {

        var width = getBitmapFromPath(path)?.width
        var height = getBitmapFromPath(path)?.height

        return (width != null && height != null && width / height == 2 / 1)
    }

    fun getDatDirFolderPath(activity: Context): String? {

        val cw = ContextWrapper(activity)
        val directory = cw.getDir("profile", Context.MODE_PRIVATE)

        if (directory.exists()) {
            return directory.absolutePath
        }

        return null
    }

    @SuppressLint("HardwareIds")
    fun getAndroidId(activity: Context): String? {
        var aid: String? = ""

        try {

            aid = Settings.Secure.getString(activity.contentResolver,
                    Settings.Secure.ANDROID_ID)

            if (aid == null) {
                aid = "No DeviceId"
            } else if (aid.isEmpty()) {
                aid = "No DeviceId"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return aid
    }


    fun getAppVersion(context: Context): Int {
        val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return pInfo.versionCode
    }

    fun deleteImageFromDataFolder(path: String) {
        Log.e("path", path)
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
    }

    /*   fun getDeviceInfo(context: Context): DeviceDetails {
           val deviceInfo = DeviceDetails()
           deviceInfo.uuid = getAndroidId(context).toString()
           deviceInfo.version = getAppVersion(context)
           return deviceInfo

       }*/

    /*  fun showCustomTab(activity: Activity, url: String) {

          var surlUpdate: String = ""
          surlUpdate = url;

          if (surlUpdate.isEmpty()) {
              return
          }

          if (!surlUpdate.contains("http")) {
              surlUpdate = "http:/$url"
          }

          val color = activity.resources.getColor(R.color.colorPrimary)
          val secondaryColor = activity.resources.getColor(R.color.colorPrimaryDark)

          val builder = CustomTabsIntent.Builder()
          builder.setToolbarColor(color)
          builder.setSecondaryToolbarColor(secondaryColor)
          builder.setStartAnimations(activity, R.anim.slide_in_left, R.anim.slide_out_left)
          builder.setExitAnimations(activity, R.anim.slide_in_right, R.anim.slide_out_right)

          val customTabsIntent = builder.build()
          customTabsIntent.launchUrl(activity, Uri.parse(surlUpdate))

      }


      fun copyToClipBoard(activity: Activity, text: String) {
          val clipboard: ClipboardManager = activity.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
          val clip = ClipData.newPlainText("label", text)
          clipboard.primaryClip = clip
          showToast(activity, activity.getString(R.string.link_copied_to_clipboard))
      }
  */


    fun radiansToDegree(radians: Float): Float {
        return (radians * 180 / Math.PI).toFloat()
    }

    fun degreeToRadians(radians: Float): Float {
        return (radians * Math.PI / 180).toFloat()
    }


    /*fun setWindowManagerCustom(materialDialog: MaterialDialog) {

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(materialDialog.window!!.attributes)
        *//*  lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        materialDialog.getWindow().setAttributes(lp);*//*
        materialDialog.window!!.attributes.windowAnimations = R.style.DialogPopupAnimation

    }*/

    fun getTypeFace(activity: Activity): Typeface? {
        val face: Typeface? = Typeface.createFromAsset(activity.assets, "fonts/MerlodNorme-Regular.ttf")
        return face
    }

}

