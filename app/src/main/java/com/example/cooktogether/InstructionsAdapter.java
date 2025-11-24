import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cooktogether.R;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.InstructionViewHolder> {

    private
    Context context;
    private String[] instruction;
    private String[] pictures;

    public InstructionsAdapter(Context context, String[] nameList) {
        this.context = context;
        this.nameList = nameList;
        this.amountList = amountList;
    }

    @NonNull
    @Override
    public com.example.cooktogether.IngredientsAdapter.InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate your row layout
        View view = LayoutInflater.from(context).inflate(R.layout.custom_lv_ingridiants, parent, false);
        return new com.example.cooktogether.IngredientsAdapter.InstructionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.cooktogether.IngredientsAdapter.IngredientViewHolder holder, int position) {
        String name = nameList[position];
        String amount = amountList[position];

        holder.nameText.setText(name);
        holder.amountText.setText(amount);
    }

    @Override
    public int getItemCount() {
        return nameList.length;
    }

    // ViewHolder inner class
    static class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView nameText;
        TextView amountText;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.ingrediantNamee);
            amountText = itemView.findViewById(R.id.ingrediantAmount);
        }
    }
}