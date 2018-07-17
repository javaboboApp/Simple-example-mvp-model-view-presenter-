package com.javabobo.projectdemo.ui.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.javabobo.projectdemo.Models.Task;
import com.javabobo.projectdemo.R;

import java.util.ArrayList;

/**
 * Created by luis on 15/02/2018.
 */

public class ItemAdapterTasks extends ArrayAdapter<Task> {

    private Listener listener;
    private ArrayList<Task> tasks = new ArrayList<>();

    private Context context;

    public ItemAdapterTasks(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public interface Listener {
        void onCheckboxChange(int idTask, boolean b);
    }

    public ItemAdapterTasks(Context context, ArrayList<Task> tasks, Listener listener) {
        super(context, 0, tasks);
        this.context = context;
        //aliasing
        this.tasks = tasks;
        this.listener = listener;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        final Task task = tasks.get(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_taks, null);
            holder = new ViewHolder();
            holder.titleTask = (TextView) convertView.findViewById(R.id.task_input);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(task.isFinish() > 0);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    task.setFinish(b ? 1 : 0);

                  listener.onCheckboxChange(task.getId(), b);

            }
        });
        holder.titleTask.setText(task.getTitle());



        return convertView;
    }

    public void addTask(Task task) {
        tasks.add(task);
        notifyDataSetChanged();
    }


    static class ViewHolder {
        TextView titleTask;
        CheckBox checkBox;
    }
}
