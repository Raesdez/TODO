package com.android.example.todo.ui;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.example.todo.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter {
    //Attributes
    //private Context mContext;
    String taskType;
    List<Task> lista;


    //The constructor gets the Context from where it was created

    public TaskAdapter(String taskType, List<Task> lista) {
        this.lista = lista;
        this.taskType = taskType;
    }


    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card, parent, false);
        return new TasksViewHolder(mView);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holders, final int position) {

        final TasksViewHolder holder = (TasksViewHolder) holders; //The holder and it's position

        //Put data in the cards
        holder.mTaskName.setText(lista.get(position).getTask());
        holder.task = lista.get(position);

        SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
        holder.mDue.setText(sm.format(lista.get(position).getDueTo()));
        holder.id = (lista.get(position)).getId();
        holder.mCreatedCompleted.setText(sm.format(lista.get(position).getCreates()));

        if (taskType.equals("pending")) {
            holder.mDueLabel.setText(R.string.due_to);
            holder.mCompletedCheck.setChecked(false);
        } else if (taskType.equals("completed")) {
            holder.mDueLabel.setText(R.string.completed_at);
            holder.mCompletedCheck.setChecked(true);

        }

        //TODO: Retrieve data from depending on the type of task


        //Add onClick to the check box that will mark the tasks as completed
        holder.mCompletedCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.mCompletedCheck.isChecked()) {
                    holder.mCompletedCheck.setClickable(false);
                    holder.mTrashImage.setEnabled(false);
                    //holder.mTrashImage.setVisibility(View.GONE);
                    holder.mCardView.setCardBackgroundColor(Color.LTGRAY);
                    holder.mCardView.setEnabled(false);

                    //Set completed on DB
                    holder.task.updateCompletedTask(holder.task);
                } else {

                }


            }
        });


        //Add onClick to the trash can
        holder.mTrashImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mCompletedCheck.setEnabled(false);
                holder.mTrashImage.setEnabled(false);
                holder.mCardView.setCardBackgroundColor(Color.LTGRAY);
                holder.mCardView.setEnabled(false);


                //TODO: Confirm Action

                //Delete on BD
                Task complete = new Task();
                complete.deleteTask(holder.id);

            }
        });
    }


    /**
     * [DEPRECATED] It was mandatory to override this method
     *
     * @return the size of the product list
     */
    @Override
    public int getItemCount() {
        if (lista != null) {
            return lista.size();
        } else
            return 0;
    }
}


//El view Holder es algo asi como la celda, es quien referencia a esa celda, que en este caso sería el items model
class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener,
        MenuItem.OnMenuItemClickListener {

    TextView mCreatedCompleted, mDue, mTaskName, mDueLabel, mCreatedLabel;
    ImageView mTrashImage;
    CheckBox mCompletedCheck;
    CardView mCardView;

    Long id;
    Task task;


    public TasksViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

        //Text Views
        mCreatedCompleted = itemView.findViewById(R.id.completed_at_date_text);
        mDue = itemView.findViewById(R.id.due_to_date_text);
        mTaskName = itemView.findViewById(R.id.task_name_text);
        mDueLabel = itemView.findViewById(R.id.due_to_label);

        //Image view
        mTrashImage = itemView.findViewById(R.id.trash_image);

        //Check box
        mCompletedCheck = (CheckBox) itemView.findViewById(R.id.completed_check);

        //Card View
        mCardView = (CardView) itemView.findViewById(R.id.task_card_view);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem myActionItem = menu.add(R.string.edit);
        myActionItem.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        //TODO: Call edit activity
        return true;
    }


    @Override
    public void onClick(View v) {

    }
}