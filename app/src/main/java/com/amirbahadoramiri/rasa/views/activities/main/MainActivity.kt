package com.amirbahadoramiri.rasa.views.activities.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.amirbahadoramiri.rasa.R
import com.amirbahadoramiri.rasa.views.activities.base.BaseActivity
import io.github.amirbahadoramiri.telegramdialog.DialogTwoButtonWithEditText
import io.github.amirbahadoramiri.telegramdialog.OnClickListeners

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setViewCompat()

//        val dialog = DialogOneButton(this)
//            .setTitle("Delete message")
//            .setMessage(
//                "Are you sure you want to delete this\n" +
//                        "message?"
//            )
//            .setCancelable(false)
//            .setButtonText("Delete")
//            .setButtonTextColor(R.color.main_red)
//            .setButtonRippleColor(R.color.main_red_tint)

//        val dialog = DialogOneButton(this)
//            .setTitle("حذف انبار")
//            .setMessage(
//                "موجودی و تراکنش های انبار حذف خواهند شد\n" +
//                        "آیا از حذف انبار مطمئنید ؟"
//            )
//            .setCancelable(false)
//            .setButtonText("حذف")
//            .setDirection(DialogDirection.LEFT)
//            .setButtonTextColor(R.color.main_red)
//            .setButtonRippleColor(R.color.main_red_tint)

//        dialog.setOnClickListener { dialog.dismiss() }

//        val dialog = DialogTwoButton(this)
//            .setTitle("Delete message")
//            .setMessage(
//                "Are you sure you want to delete this\n" +
//                        "message?"
//            )
//            .setCancelable(false)
//            .setButtonOneText("Cancel")
//            .setButtonOneTextColor(R.color.main_blue)
//            .setButtonOneRippleColor(R.color.main_blue_tint)
//            .setButtonTwoText("Delete")
//            .setButtonTwoTextColor(R.color.main_red)
//            .setButtonTwoRippleColor(R.color.main_red_tint)

//        val dialog = DialogTwoButton(this)
//            .setTitle("حذف انبار")
//            .setMessage(
//                "موجودی و تراکنش های انبار حذف خواهند شد\n" +
//                        "آیا از حذف انبار مطمئنید ؟"
//            )
//            .setCancelable(false)
//            .setButtonOneText("حذف")
//            .setButtonTwoText("لغو")
//            .setDirection(DialogDirection.LEFT)
//
//        dialog.setOnClickListener(object : OnClickListeners.TwoButtonListener {
//            override fun onFirstButtonClicked() {
//                dialog.dismiss();
//            }
//
//            override fun onSecondButtonClicked() {
//                dialog.dismiss()
//            }
//        })

//        val dialog = DialogOneButtonWithEditText(this)
//            .setTitle("Delete message")
//            .setMessage(
//                "Are you sure you want to delete this\n" +
//                        "message?"
//            )
//            .setCancelable(false)
//            .setButtonText("Delete")
//            .setButtonTextColor(R.color.main_blue)
//            .setButtonRippleColor(R.color.main_blue_tint)
//            .setEditTextHint("Typing...")
//            .setEditTextHintColor(R.color.main_blue_light)
//            .setEditTextColor(R.color.main_blue)
//            .setEditTextBackgroundColor(R.color.main_blue_tint)
////            ic_person size 20dpx20dp
//            .setEditTextDrawable(R.drawable.ic_person)
//            .setEditTextDrawableColor(R.color.main_blue)
//
//        dialog.setOnClickListener { text ->
//            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
//            dialog.dismiss()
//        }

        val dialog = DialogTwoButtonWithEditText(this)
            .setTitle("Delete message")
            .setMessage(
                "Are you sure you want to delete this\n" +
                        "message?"
            )
            .setCancelable(false)
            .setButtonOneText("Cancel")
            .setButtonOneTextColor(R.color.main_blue)
            .setButtonOneRippleColor(R.color.main_blue_tint)
            .setButtonTwoText("Delete")
            .setButtonTwoTextColor(R.color.main_red)
            .setButtonTwoRippleColor(R.color.main_red_tint)
            .setEditTextHint("Typing...")
            .setEditTextHintColor(R.color.main_blue_light)
            .setEditTextBackgroundColor(R.color.main_blue_tint)
//            ic_person size 20dpx20dp
            .setEditTextDrawable(R.drawable.ic_person)
            .setEditTextDrawableColor(R.color.main_blue)

        dialog.setOnClickListener(object : OnClickListeners.TwoButtonWithEditTextListener {
            override fun onFirstButtonClicked(text: String) {
                Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            override fun onSecondButtonClicked(text: String) {
                Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        })

        dialog.show()

    }
}