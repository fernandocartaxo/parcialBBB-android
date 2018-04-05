package com.ctx.parcialbbb;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctx.parcialbbb.ParticipanteFragment.OnListFragmentInteractionListener;
import com.ctx.parcialbbb.modelos.Participante;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.text.DecimalFormat;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Participante} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class ParticipanteRecyclerViewAdapter extends RecyclerView.Adapter<ParticipanteRecyclerViewAdapter.ViewHolder> {

    private final List<Participante> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ParticipanteRecyclerViewAdapter(List<Participante> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_participante, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Participante particiapente = mValues.get(position);
        holder.mItem = particiapente;
        holder.mNome.setText(particiapente.getName());

        holder.mPorcentual.setText(particiapente.getPercent().toString());
        Picasso.get()
                .load(particiapente.getUrlImage())
                .resizeDimen(R.dimen.defaultWidth, R.dimen.defaultHeight)
                .transform(new CropCircleTransformation())
                .into(holder.mImagem);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNome;
        public final TextView mPorcentual;
        public final ImageView mImagem;
        public Participante mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNome = view.findViewById(R.id.txtNome);
            mPorcentual = view.findViewById(R.id.txtPorcentual);
            mImagem = view.findViewById(R.id.imagem);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNome.getText() + "'";
        }
    }
}
