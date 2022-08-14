package com.example.quickquiz.fragment

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.quickquiz.R
import com.example.quickquiz.viewmodel.QuizViewModel
import com.google.android.gms.ads.*
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.frag_details.*
import android.provider.Settings.Secure;
import com.example.quickquiz.activities.ActBase
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.util.*


class FragDetails : FragBase() {

    private var quizViewModel: QuizViewModel? = null
    private var position: Int = 0
    private var quizId: String = ""
    private val getPosition: FragDetailsArgs by navArgs()
    private var totalQue: Long = 0
    private var adView: AdView? = null
    private var android_id: String = ""
    private var mInterstitialAd: InterstitialAd? = null
    private var mCountDownTimer: CountDownTimer? = null
    private var mAdIsLoading: Boolean = false
    private var adRequest: AdRequest? = null
    private val TAG: String = "FragDetails"


    override fun getFragView() = R.layout.frag_details

    override fun setUpView(view: View) {
        position = getPosition.position
        adRequest = AdRequest.Builder().build()
        loadAd()

        android_id = Secure.getString(baseContext.contentResolver, Secure.ANDROID_ID)
        MobileAds.initialize(baseContext) {}
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf(android_id)).build()
        )

        var uniqueID = UUID.randomUUID().toString()

        Log.e("Device ID", "$android_id, $uniqueID")

        Log.e("getPosition", position.toString())

        initViewModel()
        initClick()


        MobileAds.initialize(baseContext) {

            Toast.makeText(baseContext, "Ad is published", Toast.LENGTH_SHORT).show()
        }

        attachBannerAd()
        attachInterstitialAd()

    }

    private fun attachInterstitialAd() {

        InterstitialAd.load(baseContext,
            resources.getString(R.string.interstitial_ad_key),
            adRequest!!,
            object : InterstitialAdLoadCallback() {

                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    Log.e("Interstitial Error", error.message)
                    mInterstitialAd = null
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    super.onAdLoaded(ad)

                    mInterstitialAd = ad
                    Toast.makeText(baseContext,"Ad is Loaded", Toast.LENGTH_SHORT).show()


                }

            })

    }

    private fun loadAd() {
        quizViewModel = ViewModelProvider(baseContext)[QuizViewModel::class.java]
        adView = AdView(baseContext)
    }


    private fun attachBannerAd() {
        adView?.setAdSize(AdSize.BANNER)
        adView?.adUnitId = resources.getString(R.string.banner_ad_key)
        adRequest?.let { ad_banner.loadAd(it) }

        adView?.adListener = object : AdListener() {

            override fun onAdFailedToLoad(error: LoadAdError) {
                super.onAdFailedToLoad(error)

                Log.e("ad_banner_msg", error.message)

            }

        }

    }


    private fun initClick() {
        btnQuizStart.setOnClickListener {
            showInterstitialAd()

        }
    }

    private fun showInterstitialAd() {

        if (mInterstitialAd !=null){
            mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                override fun onAdClicked() {
                    // Called when a click is recorded for an ad.
                    Log.e(TAG, "Ad was clicked.")
                }

                override fun onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    Log.d(TAG, "Ad dismissed fullscreen content.")
                    mInterstitialAd = null
                    loadNextFrag()
                }



                override fun onAdImpression() {
                    // Called when an impression is recorded for an ad.
                    Log.d(TAG, "Ad recorded an impression.")
                }

                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d(TAG, "Ad showed fullscreen content.")
                }
            }
            mInterstitialAd?.show(baseContext)
        }else{
            loadNextFrag()
            Toast.makeText(baseContext, "Ad is not Loaded", Toast.LENGTH_SHORT).show()
        }

    }

    private fun loadNextFrag() {
        if (quizId.isNotEmpty()) {


             val passingAction =
                 FragDetailsDirections.actionFragDetailsToFragQuiz(totalQue, quizId)

             navController?.navigate(passingAction)
        }
    }

    private fun initViewModel() {
        quizViewModel?.getQuizData()?.observe(viewLifecycleOwner, Observer { data ->
            txtQuizDetailTitle.text = data[position].name
            txtQuizDetailDesc.text = data[position].desc
            txtDetailDiffValue.text = data[position].level
            txtDetailTotalQueVal.text = data[position].questions.toString()
            quizId = data[position].quiz_id
            totalQue = data[position].questions
            getResultFromFirebase()
        })
    }

    private fun getResultFromFirebase() {
        fireStoreRep.collection("QuizList").document(quizId).collection("Result").document(getUid())
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val res = task.result
                    if (res.exists()) {
                        showResult(res)
                    } else {
                        txtDetailLastScoreVal.text = resources.getString(R.string.na)
                    }

                } else {
                    txtDetailLastScoreVal.text = resources.getString(R.string.na)
                }
            }
    }

    private fun showResult(res: DocumentSnapshot) {


        val correct = res["correct_ans"] as Long
        val wrong = res["wrong_ans"] as Long
        val missed = res["unanswered"] as Long
        val total = correct + wrong + missed
        val per = (correct * 100) / total

        txtDetailLastScoreVal.text = per.toString()


    }


}