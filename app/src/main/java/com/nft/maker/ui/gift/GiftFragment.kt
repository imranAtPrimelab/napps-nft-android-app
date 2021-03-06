package com.nft.maker.ui.gift

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nft.maker.extensions.observeResultFlow
import com.nft.maker.extensions.viewBinding
import com.nft.maker.databinding.FragmentGiftNftBinding
import com.nft.maker.ui.base.BaseFragment
import com.nft.maker.util.adapters.ContactListAdapter
import com.nft.maker.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent
import android.graphics.Color
import com.nft.maker.model.Contact
import com.nft.maker.ui.base.activity.BaseActivity
import com.nft.maker.ui.base.adapter.MULTI

import androidx.core.content.ContextCompat

import android.graphics.drawable.Drawable
import androidx.core.view.ViewCompat
import com.nft.maker.R
import com.nft.maker.util.AppConstants


@AndroidEntryPoint
class GiftFragment : BaseFragment(R.layout.fragment_gift_nft) {

    private val binding by viewBinding(FragmentGiftNftBinding::bind)
    private val viewModel by activityViewModels<ContactViewModel>()
    private var permissionGranted = false

    private val contactListAdapter by lazy {
        return@lazy ContactListAdapter(activity as Context) { contact, position ->
            selectContact(contact, position)
        }.setMode(MULTI)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                permissionGranted = granted
                AppConstants.logAppsFlyerEvent(AppConstants.CONTACTS_PERMISSION_GRANTED_EVENT_NAME, requireContext())
                getContactList()
            }
        }


    private fun selectContact(contact: Contact, position: Int) {
        contactListAdapter.toggleSelection(position)
        if(!viewModel.selectedHashSet!!.containsKey(contact))
        viewModel.selectedHashSet!![contact] = position
        validateSendGiftBtn()
    }

    private fun validateSendGiftBtn(){
        val selectedContacts = contactListAdapter.selectedPosition.mapNotNull { contactListAdapter.getItemAtPosition(it) }
        if (selectedContacts.isEmpty()) {
            binding.sendGift.isEnabled = false
            val background: Drawable = binding.sendGift.getBackground()
            background.setTint(ContextCompat.getColor(requireContext(), R.color.light_grey))
            ViewCompat.setBackground(binding.sendGift, background)
        } else {
            binding.sendGift.isEnabled = true
            val background: Drawable = binding.sendGift.getBackground()
            background.setTint(ContextCompat.getColor(requireContext(), R.color.blue))
            ViewCompat.setBackground(binding.sendGift, background)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.isFocusableInTouchMode = true
        view.requestFocus()

        view.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        val i = Intent()
                        i.action = Intent.ACTION_MAIN
                        i.addCategory(Intent.CATEGORY_HOME)
                        view.context.startActivity(i)
                        return true
                    }
                }
                return false
            }
        })
        initListeners()
        initViews()
    }
    private fun initViews() {
        binding.contactList.adapter = contactListAdapter
        binding.contactList.callOnClick()
        binding.searchView.findViewById<View>(androidx.appcompat.R.id.search_plate)
            .setBackgroundColor(Color.TRANSPARENT)
        validateSendGiftBtn()
    }


    private fun initListeners() {
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (permissionGranted) {
                    (binding.contactList.adapter as ContactListAdapter).filter(
                        newText!!,
                        viewModel.itemsCopy!!
                    )

                    val contacts = mutableListOf<Contact>()
                    val currentData =
                        (binding.contactList.adapter as ContactListAdapter).getData()!!
                    for (i in 0 until viewModel.selectedHashSet!!.size)
                        if (currentData.contains(viewModel.selectedHashSet!!.keys.elementAt(i))) {
                            contacts.add(viewModel.selectedHashSet!!.keys.elementAt(i))
                        }

                    for (i in currentData.indices) {
                        if (contacts.contains(currentData[i]))
                            selectContact(currentData[i], i)
                    }

                    return true
                }
                else
                {
                    requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                    return true
                }
            }
        })

        /*binding.searchView.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                (binding.contactList.adapter as ContactListAdapter).filter(binding.searchView.text.toString())
            }
            false
        }*/

        binding.sendGift.setOnClickListener {
            val selectedContacts = contactListAdapter.selectedPosition.mapNotNull { contactListAdapter.getItemAtPosition(it) }

            if (selectedContacts.isEmpty()) {
                // error select empty people
                Toast.makeText(requireContext(),"no contacts selected!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                (this.activity as BaseActivity).showProgressDialog()
                observeResultFlow(
                    viewModel.postLocalContact(
                        selectedContacts
                    ), successHandler = {
                        val bundle = Bundle()
                        bundle.putInt("onBoarding", View.GONE)
                        findNavController().navigate(R.id.toCreateNft, bundle)
                    }, errorHandler = {
                        Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
                        (this.activity as BaseActivity).dismissProgressDialog()
                    }
                )
            }
        }

        /*binding.btnClose.setOnClickListener {
            findNavController().navigate(R.id.toMain)
        }*/
        requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)

        binding.cvSearch.setOnClickListener {
            binding.searchView.onActionViewExpanded()
        }

    }


    private fun getContactList() {
        (this.activity as BaseActivity).showProgressDialog()
        observeResultFlow(
            viewModel.getLocalContacts(
            ), successHandler = {
                val currentData = it
                contactListAdapter.setData(it)
                for(i in currentData.indices){
                    selectContact(currentData[i],i)
                }
                viewModel.itemsCopy = it
                (this.activity as BaseActivity).dismissProgressDialog()
            }, errorHandler = {
                Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT)
                    .show()
                (this.activity as BaseActivity).dismissProgressDialog()
            }
        )
    }




}