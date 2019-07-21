package com.capotter.urbandictionary.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capotter.urbandictionary.R
import com.capotter.urbandictionary.model.Definition
import kotlinx.android.synthetic.main.definition_list_item.view.*

class DefinitionAdapter(val definitionList: List<Definition>?) : RecyclerView.Adapter<DefinitionAdapter.ViewHolder>() {

    /**
     * Get amount of items in definitionList
     */
    override fun getItemCount() = definitionList!!.size

    private var mContext: Context? = null

    /**
     * Attach and inflate retrieved items to individual list_item layout
     * @param p0 parent viewGroup
     * @param p1 viewType
     */
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DefinitionAdapter.ViewHolder {
        this.mContext = p0.context

        return ViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.definition_list_item, p0, false
            )
        )
    }

    /**
     * Bind each variable to it's associated view
     * @param p0 parent ViewHolder
     * @param p1 position of item
     */
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        val mDefinition = definitionList!!.get(p1)

        if(mDefinition.word != null){
            p0.definitionWord.text = mDefinition.word
        }

        if(mDefinition.definition != null) {
            p0.definitionDefinition.text = mDefinition.definition
        }

        if(mDefinition.thumbs_up != null) {
            p0.thumbsUpCount.text = mDefinition.thumbs_up.toString()
        }

        if(mDefinition.thumbs_down != null) {
            p0.thumbsDownCount.text = mDefinition.thumbs_down.toString()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val definitionWord:TextView = itemView.findViewById(R.id.wordText)
        val definitionDefinition:TextView = itemView.findViewById(R.id.definitionText)
        val thumbsUpCount:TextView = itemView.findViewById(R.id.thumbs_up_count)
        val thumbsDownCount:TextView = itemView.findViewById(R.id.thumbs_down_count)
    }
}