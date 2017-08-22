package com.fransunisoft.stockhawk.rest;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fransunisoft.stockhawk.rest.CursorRecyclerViewAdapter;
import com.fransunisoft.stockhawk.R;
import com.fransunisoft.stockhawk.data.QuoteColumns;
import com.fransunisoft.stockhawk.data.QuoteProvider;
import com.fransunisoft.stockhawk.touch_helper.ItemTouchHelperAdapter;
import com.fransunisoft.stockhawk.touch_helper.ItemTouchHelperViewHolder;


@TargetApi(16)
public class QuoteCursorAdapter extends CursorRecyclerViewAdapter<QuoteCursorAdapter.ViewHolder>
    implements ItemTouchHelperAdapter{
  private Context mContext;
  private static Typeface robotoLight;
  //private final OnStartDragListener mDragListener;
  private boolean isPercent;
  public QuoteCursorAdapter(Context context, Cursor cursor){
    super(context, cursor);
    //mDragListener = dragListener;
    mContext = context;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    robotoLight = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
    View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_quote, parent, false);
    ViewHolder vh = new ViewHolder(itemView);
    return vh;
  }

  @Override
  public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor){
    viewHolder.symbol.setText(cursor.getString(cursor.getColumnIndex("symbol")));
    viewHolder.bidPrice.setText(cursor.getString(cursor.getColumnIndex("bid_price")));
    if (cursor.getInt(cursor.getColumnIndex("is_up")) == 1){
      viewHolder.change.setBackground(mContext.getResources()
          .getDrawable(R.drawable.percent_change_pill_green));
    } else{
      viewHolder.change.setBackground(mContext.getResources()
          .getDrawable(R.drawable.percent_change_pill_red));
    }
    if (com.fransunisoft.stockhawk.rest.Utils.showPercent){
      viewHolder.change.setText(cursor.getString(cursor.getColumnIndex("percent_change")));
    } else{
      viewHolder.change.setText(cursor.getString(cursor.getColumnIndex("change")));
    }
  }

  @Override public void onItemDismiss(int position) {
    long cursorId = getItemId(position);
    Cursor c = getCursor();
    String symbol = c.getString(c.getColumnIndex(QuoteColumns.SYMBOL));
    mContext.getContentResolver().delete(QuoteProvider.Quotes.withSymbol(symbol), null, null);
    notifyItemRemoved(position);
  }

  @Override public int getItemCount() {
    return super.getItemCount();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder
      implements ItemTouchHelperViewHolder{
    public final TextView symbol;
    public final TextView bidPrice;
    public final TextView change;
    public ViewHolder(View itemView){
      super(itemView);
      symbol = (TextView) itemView.findViewById(R.id.stock_symbol);
      symbol.setTypeface(robotoLight);
      bidPrice = (TextView) itemView.findViewById(R.id.bid_price);
      change = (TextView) itemView.findViewById(R.id.change);
    }

    @Override
    public void onItemSelected(){
      itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear(){
      itemView.setBackgroundColor(0);
    }
  }
}
