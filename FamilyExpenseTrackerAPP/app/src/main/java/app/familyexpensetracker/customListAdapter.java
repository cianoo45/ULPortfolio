package app.familyexpensetracker;

/**
 * Created by user on 26/03/2018.
 */

        import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;


public class customListAdapter extends ArrayAdapter<String> {

    private final Activity context;



    public customListAdapter(Activity context) {
        super(context, R.layout.customlayout,expenselist.result1);
        // TODO Auto-generated constructor stub


        this.context=context;

    }

    public View getView(int position,View view,ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.customlayout, null,true);


        ImageView imageView = (ImageView) rowView.findViewById(R.id.itemIV);
        TextView extratxt = (TextView) rowView.findViewById(R.id.itemTV);

        if(expenselist.result1.get(position).contains("Food")) {
            imageView.setImageResource(R.drawable.d);
        }
        else if (expenselist.result1.get(position).contains("Alcohol")) {
            imageView.setImageResource(R.drawable.a);
        }
        else if (expenselist.result1.get(position).contains("Travel")){
            imageView.setImageResource(R.drawable.air);
        }

        else if (expenselist.result1.get(position).contains("Leisure")){
            imageView.setImageResource(R.drawable.lesure);
        }

        else if (expenselist.result1.get(position).contains("Health")){
            imageView.setImageResource(R.drawable.health);
        }

        else if (expenselist.result1.get(position).contains("Bills")){
            imageView.setImageResource(R.drawable.bills);
        }



        extratxt.setText(expenselist.result1.get(position));


        return rowView;

    };
}