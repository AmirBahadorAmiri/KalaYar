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
import com.amirbahadoramiri.kalayar.databinding.ReportFragmentBinding
import com.amirbahadoramiri.kalayar.databinding.TransactionItemRecyclerviewItemBinding
import com.amirbahadoramiri.kalayar.databinding.TransactionShowSheetBinding
import com.amirbahadoramiri.kalayar.domain.models.ReportData
import com.amirbahadoramiri.kalayar.domain.models.Store
import com.amirbahadoramiri.kalayar.domain.models.Transaction
import com.amirbahadoramiri.kalayar.domain.models.TransactionItem
import com.amirbahadoramiri.kalayar.tools.text_utils.TextUtils
import java.io.File
import java.io.FileOutputStream

object PDFUtils {

    fun generateAndShareReportPDF(
        context: Context,
        reportData: ReportData,
        store: Store?
    ) {
        val inflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ReportFragmentBinding>(
            inflater,
            R.layout.report_fragment,
            null,
            false
        )

        // Populate Data
        binding.totalValue.text = "${TextUtils.numberFormat(reportData.totalInventoryValue)} ${context.getString(R.string.toman)}"
        
        binding.rowTotalItems.rowTitle.text = context.getString(R.string.total_products_count)
        binding.rowTotalItems.rowValue.text = reportData.totalProductsCount.toString()
        
        binding.rowProductTypes.rowTitle.text = context.getString(R.string.product_types_count)
        binding.rowProductTypes.rowValue.text = reportData.productTypesCount.toString()

        binding.rowLowStock.rowTitle.text = context.getString(R.string.low_stock_products)
        binding.rowLowStock.rowValue.text = reportData.lowStockCount.toString()

        binding.rowHighValue.rowTitle.text = context.getString(R.string.high_value_stock)
        binding.rowHighValue.rowValue.text = reportData.highValueProduct

        binding.rowSalesToday.rowTitle.text = context.getString(R.string.sales_today)
        binding.rowSalesToday.rowValue.text = "${TextUtils.numberFormat(reportData.salesToday)} ${context.getString(R.string.toman)}"

        binding.rowSales1m.rowTitle.text = context.getString(R.string.sales_1_month)
        binding.rowSales1m.rowValue.text = "${TextUtils.numberFormat(reportData.sales1Month)} ${context.getString(R.string.toman)}"

        binding.rowSales3m.rowTitle.text = context.getString(R.string.sales_3_months)
        binding.rowSales3m.rowValue.text = "${TextUtils.numberFormat(reportData.sales3Months)} ${context.getString(R.string.toman)}"

        binding.rowSales6m.rowTitle.text = context.getString(R.string.sales_6_months)
        binding.rowSales6m.rowValue.text = "${TextUtils.numberFormat(reportData.sales6Months)} ${context.getString(R.string.toman)}"

        binding.rowSales1y.rowTitle.text = context.getString(R.string.sales_1_year)
        binding.rowSales1y.rowValue.text = "${TextUtils.numberFormat(reportData.sales1Year)} ${context.getString(R.string.toman)}"

        binding.rowTotalSales.rowTitle.text = context.getString(R.string.total_sales)
        binding.rowTotalSales.rowValue.text = "${TextUtils.numberFormat(reportData.totalSales)} ${context.getString(R.string.toman)}"

        binding.rowTotalProfit.rowTitle.text = context.getString(R.string.total_profit)
        binding.rowTotalProfit.rowValue.text = "${TextUtils.numberFormat(reportData.totalProfit)} ${context.getString(R.string.toman)}"

        binding.rowTopSelling.rowTitle.text = context.getString(R.string.top_selling_product)
        binding.rowTopSelling.rowValue.text = reportData.topSellingProduct

        binding.rowLeastSelling.rowTitle.text = context.getString(R.string.least_selling_product)
        binding.rowLeastSelling.rowValue.text = reportData.leastSellingProduct

        binding.rowTransactionCount.rowTitle.text = context.getString(R.string.total_transactions_count)
        binding.rowTransactionCount.rowValue.text = reportData.transactionCount.toString()

        binding.executePendingBindings()

        // UI Adjustments for PDF
        binding.backBtn.visibility = View.GONE
        binding.printBtn.visibility = View.GONE
        if (store != null) {
            binding.title.text = store.store_name
        }

        // Force RTL for Persian alignment
        val root = binding.root
        root.layoutDirection = View.LAYOUT_DIRECTION_RTL

        // Measure and layout
        val width = 1080
        
        // Ensure NestedScrollView expands to show all content
        binding.reportScrollView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

        // Force the root to have a fixed width and wrap content height
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

        // Save and Share
        val fileName = "report_${System.currentTimeMillis()}.pdf"
        val file = File(context.cacheDir, fileName)
        try {
            val fos = FileOutputStream(file)
            pdfDocument.writeTo(fos)
            fos.close()
            pdfDocument.close()
            
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

        // Force RTL for Persian alignment
        val root = binding.root
        root.layoutDirection = View.LAYOUT_DIRECTION_RTL

        // Measure and layout at a fixed width for the PDF
        val width = 1080
        
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
