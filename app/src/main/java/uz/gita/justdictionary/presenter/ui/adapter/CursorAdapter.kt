package uz.gita.justdictionary.presenter.ui.adapter

import android.annotation.SuppressLint
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uz.gita.justdictionary.R
import uz.gita.justdictionary.databinding.ItemWordBinding

class CursorAdapter: RecyclerView.Adapter<CursorAdapter.WordViewHolder>() {
    private var cursor: Cursor? = null
    private var onClickRememberListener:((Int)-> Unit) ?= null

    @SuppressLint("NotifyDataSetChanged")
    fun submitCursor(_cursor: Cursor) {
        cursor = _cursor
        notifyDataSetChanged()
    }

    @SuppressLint("Range", "NotifyDataSetChanged")
    inner class WordViewHolder(private val binding: ItemWordBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnSave.setOnClickListener {
                cursor?.let {
                    it.moveToPosition(absoluteAdapterPosition)
                    val id = it.getInt(it.getColumnIndex("id"))
                    onClickRememberListener?.invoke(id)
                }
            }
        }

        @SuppressLint("Range")
        fun bind() {
            cursor?.let {
                it.moveToPosition(absoluteAdapterPosition)
                binding.word.text = it.getString(it.getColumnIndex("word"))
                binding.wordType.text = it.getString(it.getColumnIndex("wordtype"))
                if(it.getInt(it.getColumnIndex("isRemember")) == 0)
                    binding.btnSave.setImageResource(R.drawable.ic_unsave)
                else
                    binding.btnSave.setImageResource(R.drawable.ic_save)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder = WordViewHolder(
        ItemWordBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        )
    )

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) = holder.bind()

    override fun getItemCount(): Int {
        return if(cursor == null) 0 else cursor!!.count
    }

    fun setOnClickRememberListener(block: (Int) -> Unit) {
        onClickRememberListener = block
    }

}