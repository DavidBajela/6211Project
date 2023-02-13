package com.david.bajela.a6211project.UI.Fragments.RecyclerView

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.DialogInterface
import android.hardware.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation
import com.david.bajela.a6211project.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.david.bajela.a6211project.Data.Repo
import com.david.bajela.a6211project.UI.Fragments.FragListerner
import com.david.bajela.a6211project.databinding.FragmentMessageBinding

class MesageFragment : Fragment(R.layout.fragment_message),
    MyMesageRecyclerViewAdapter.messageListerner, SensorEventListener {

    private lateinit var binding: FragmentMessageBinding
    private lateinit var l: FragListerner
    lateinit var ith: ItemTouchHelper
    lateinit var a: MyMesageRecyclerViewAdapter
    lateinit var acel: Sensor
    lateinit var sm: SensorManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        a = MyMesageRecyclerViewAdapter(Repo.notes, this)
        binding = FragmentMessageBinding.bind(view)
        binding.apply {
            mRV.layoutManager = LinearLayoutManager(context)
            mRV.adapter = a
            mRV.setHasFixedSize(true)
            fabAdd.setOnClickListener { addNewNote() }
        }

        ith = ItemTouchHelper(o)
        ith.attachToRecyclerView(binding.mRV)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragListerner)
            l = context
        sm = context.getSystemService(SENSOR_SERVICE) as SensorManager
        acel = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
      //  acel
        sm.registerListener(this, acel, SensorManager.SENSOR_DELAY_NORMAL)
        l.welcomeUser(Repo.user.firstname)
    }

    override fun onPause() {
        super.onPause()
        sm.unregisterListener(this);
    }

    override fun onResume() {
        super.onResume()
        sm.registerListener(this, acel, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private val o =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

               return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Repo.deleteSelectedNote(viewHolder.adapterPosition)
                a.notifyDataSetChanged()
                //viewHolder
                //Remove(pos)
            }
        }

    fun deleteall() {
        Repo.deleteAllnote()
        a.notifyDataSetChanged()
    }

    fun addNewNote() {
        Repo.createNewNote()
        gotomessage()
    }

    fun gotomessage() {
        this.view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_mesageFragment_to_noteFragment)
        }
    }

    override fun onMessageclicked(position: Int) {
        Repo.selectNote(position)
        gotomessage()
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if (event?.values?.sum()!! > 18) {
            l.dialog("Delete ALL!!!","Are you shore you want to do this"){ dialogInterface: DialogInterface, i: Int ->
                if(i==-1)deleteall()
            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}
