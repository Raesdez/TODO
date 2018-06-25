package com.android.example.todo.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.example.todo.R;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter {
    //Attributes
    //private Context mContext;
    String taskType;
    ArrayList<String> lista;


    //The constructor gets the Context from where it was created

    public TaskAdapter(String taskType, ArrayList<String> lista) {
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
        holder.mTaskName.setText(lista.get(position));

        if(taskType.equals("pending"))
        {
            holder.mDueLabel.setText("Due to:");
        }
        else if (taskType.equals("completed"))
        {
            holder.mDueLabel.setText("Completed at:");
        }

        //TODO: Retrieve data from depending on the type of task

        //TODO: Put data in the cards

        //TODO: Add onClick to the check box that will mark the takss as completed
        holder.mCompletedCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //holder.mCompletedCheck.setEnabled(false);
                holder.mCompletedCheck.setClickable(false);
                holder.mTrashImage.setEnabled(false);
                holder.mTrashImage.setVisibility(View.GONE);
                holder.mCardView.setCardBackgroundColor(Color.LTGRAY);

                //TODO: Set completed on DB

            }
        });


        //TODO: Add onClick to the trash can
        holder.mTrashImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mCompletedCheck.setEnabled(false);
                holder.mTrashImage.setEnabled(false);
                holder.mCardView.setCardBackgroundColor(Color.LTGRAY);

                //TODO: Confirm Action

                //TODO: Delete on BD

            }
        });


        //Retrieve all the information from the list using the position and adding it to the card
       /* holder.mImage.setImageResource(mProductList.get(position).getImage());
        holder.mTitle.setText(mProductList.get(position).getName());
        holder.mCategory.setText(mProductList.get(position).getCategory());
        holder.mMaker.setText(mProductList.get(position).getMaker());
        holder.mWeight.setText(String.format("%s Kg", Double.toString(mProductList.get(position).getWeight())));
        holder.mPrice.setText(String.format("%s $", Double.toString(mProductList.get(position).getPrice()))); */


        //If the selected product list has products, it selects the product on the card
        //selectDeselectCard(holder, position);


        /**
         * A click listener for the card view that selects or deselects the products and updates the
         * list, it also doesn't let you add more products than the max
         */
       /* holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mSelectedProductsList.size() == 0) {
                    holder.mCheck.setChecked(true);                      //Check the check box
                    mSelectedProductsList.add(mProductList.get(position)); //Add the product to the list of selected
                    System.out.println("Added" + mContext.getString(mProductList.get(position).getName()));


                } else {


                    Iterator iterator = mSelectedProductsList.iterator();
                    Product aux=null;
                    boolean delete = false;

                    while(iterator.hasNext())
                    {
                        Product compare = (Product)iterator.next();
                        if(mContext.getString(compare.getName()).equals(holder.mTitle.getText()))
                        {
                            delete=true;
                            holder.mCheck.setChecked(false);                             //Uncheck the check box
                            aux = compare;
                            break;

                        }
                        else                                                        //It has been selected
                        {
                            if (mSelectedProductsList.size() != MAX_PRODUCTS) {
                                holder.mCheck.setChecked(true);                      //Check the check box
                                aux = mProductList.get(position);
                                //delete = false;
                                // mSelectedProductsList.add(mProductList.get(position)); //Add the product to the list of selected
                                System.out.println("Added" + mContext.getString(mProductList.get(position).getName()));


                            } else {
                                Snackbar.make(view, R.string.alert_maximun_products, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                break;
                            }
                        }

                    }
                    if(delete)
                        mSelectedProductsList.remove(aux);
                    else {
                        mSelectedProductsList.add(aux);
                    }

                }
            }
        }); */
    }


    /**
     * At creation of each card, it selects those that were already selected before calling the product list view
     *
     * @param holder
     * @param position
     */
    /*private void selectDeselectCard(ProductViewHolder holder, int position) {

        for(Product product: mSelectedProductsList)
        {

            if (mContext.getString(product.getName()).equals(holder.mTitle.getText()))
                holder.mCheck.setChecked(true);
        }


    } */

    /**
     * [DEPRECATED] It was mandatory to override this method
     *
     * @return the size of the product list
     */
    @Override
    public int getItemCount() {
        return lista.size();
    }
}


//El view Holder es algo asi como la celda, es quien referencia a esa celda, que en este caso sería el items model
class TasksViewHolder extends RecyclerView.ViewHolder {

    TextView mCreatedCompleted, mDue, mTaskName,mDueLabel,mCreatedLabel;
    ImageView mTrashImage;
    CheckBox mCompletedCheck;
    CardView mCardView;

    //TODO: I don't know if I can put the object parameters here
    String id = "";


    public TasksViewHolder(View itemView) {
        super(itemView);

        //Text Views
        mCreatedCompleted = itemView.findViewById(R.id.completed_at_date_text);
        mDue = itemView.findViewById(R.id.due_to_date_text);
        mTaskName = itemView.findViewById(R.id.task_name_text);
        mDueLabel =  itemView.findViewById(R.id.due_to_label);

        //Image view
        mTrashImage = itemView.findViewById(R.id.trash_image);

        //Check box
        mCompletedCheck = (CheckBox) itemView.findViewById(R.id.completed_check);

        //Card View
        mCardView = (CardView) itemView.findViewById(R.id.task_card_view);

    }


}