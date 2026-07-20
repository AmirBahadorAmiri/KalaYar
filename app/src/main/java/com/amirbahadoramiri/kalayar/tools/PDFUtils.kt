package com.amirbahadoramiri.kalayar.tools

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.TransactionItemRecyclerviewItemBinding
import com.amirbahadoramiri.kalayar.databinding.TransactionShowSheetBinding
import com.amirbahadoramiri.kalayar.domain.models.Store
import com.amirbahadoramiri.kalayar.domain.models.Transaction
import com.amirbahadoramiri.kalayar.domain.models.TransactionItem
import com.amirbahadoramiri.kalayar.tools.text_utils.TextUtils
import java.io.File
import java.io.FileOutputStream

object PDFUtils {

    fun generateAndShareTransactionPDF(
        context: Context,
        transaction: Transaction,
        items: List<TransactionItem>,
        totalPrice: Long,
        store: Store?
    ) {
        val inflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<TransactionShowSheetBinding>(
            inflater,
            R.layout.transaction_show_sheet,
            null,
            false
        )
        binding.transaction = transaction
        binding.store = store
        binding.totalPriceValue.text = "${TextUtils.numberFormat(totalPrice)} ${context.getString(R.string.toman)}"

        // Prepare view for PDF
        binding.itemsRecyclerview.visibility = View.GONE
        binding.handle.visibility = View.GONE
        binding.printBtn.visibility = View.GONE
        binding.itemsContainerPdf.visibility = View.VISIBLE
        binding.storeContainerPdf.visibility = if (store != null) View.VISIBLE else View.GONE

        items.forEach { item ->
            val itemBinding = DataBindingUtil.inflate<TransactionItemRecyclerviewItemBinding>(
                inflater,
                R.layout.transaction_item_recyclerview_item,
                binding.itemsContainerPdf,
                false
            )
            itemBinding.item = item
            itemBinding.executePendingBindings()
            binding.itemsContainerPdf.addView(itemBinding.root)
        }

        binding.executePendingBindings()

        // Measure and layout at a fixed width for the PDF
        val width = 1080
        val root = binding.root
        root.layoutParams = ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        
        // Force measurement
        val widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        root.measure(widthSpec, heightSpec)
        root.layout(0, 0, root.measuredWidth, root.measuredHeight)

        // Capture bitmap
        val bitmap = Bitmap.createBitmap(root.measuredWidth, root.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(context.getColor(R.color.kalayar_page_background_color))
        root.draw(canvas)

        // Create PDF
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        page.canvas.drawBitmap(bitmap, 0f, 0f, null)
        pdfDocument.finishPage(page)

        // Save
        val fileName = "transaction_${transaction.transaction_id ?: System.currentTimeMillis()}.pdf"
        val file = File(context.cacheDir, fileName)
        try {
            val fos = FileOutputStream(file)
            pdfDocument.writeTo(fos)
            fos.close()
            pdfDocument.close()
            
            // Share
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.print)))
            
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
