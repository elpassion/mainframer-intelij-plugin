package com.elpassion.mainframerplugin.action.configure.selector.ui

import com.elpassion.mainframerplugin.action.configure.selector.SelectorItem
import com.intellij.ui.CheckBoxList
import com.jgoodies.common.collect.ArrayListModel
import javax.swing.JCheckBox
import javax.swing.ListSelectionModel

class SelectorList : CheckBoxList<SelectorItem>() {

    init {
        selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
    }

    var items: List<SelectorItem> = emptyList()
        set(value) {
            model = value.asListModel()
            field = value
        }
        get() = field.mapIndexed { index, selectorItem -> selectorItem.copy(isSelected = isItemSelected(index)) }

    @Suppress("unchecked_cast")
    private val listModel get() = model as ArrayListModel<JCheckBox>

    fun selectAll() {
        listModel.forEachIndexed { index, checkBox ->
            checkBox.isSelected = true
            listModel.fireContentsChanged(index)
        }
    }

    fun unselectAll() {
        listModel.forEachIndexed { index, checkBox ->
            checkBox.isSelected = false
            listModel.fireContentsChanged(index)
        }
    }

    private fun List<SelectorItem>.asListModel() = ArrayListModel(map { createCheckBox(it) })

    private fun createCheckBox(item: SelectorItem) = JCheckBox(item.name).apply { isSelected = item.isSelected }
}