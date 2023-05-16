package com.example.connectus

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.connectus.databinding.FragmentHomeBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*


class HomeFragment : Fragment() {
    private lateinit var dbReference: DatabaseReference
    private lateinit var adapter: MyAdapter
    private lateinit var postList : ArrayList<PostData>

    private lateinit var _binding : FragmentHomeBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbReference = FirebaseDatabase.getInstance().getReference("posts")
        postList = arrayListOf<PostData>()
        adapter = MyAdapter(postList)

        binding.recylerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.recylerView.setHasFixedSize(true)
        getPostData()
        view.findViewById<FloatingActionButton>(R.id.addPostBtn).setOnClickListener {
            val intent = Intent(requireContext(),AddPostActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


    private fun getPostData(){

        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postList.clear()
                if (snapshot.exists()){
                    for (postSnap in snapshot.children){
                        val postData = postSnap.getValue(PostData::class.java)
                        postList.add(postData!!)
                    }
                    binding.recylerView.adapter=adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),"${error.message}",Toast.LENGTH_SHORT).show()
            }

        })
    }
}