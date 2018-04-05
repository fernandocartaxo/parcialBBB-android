package com.ctx.parcialbbb.singletons;

import com.ctx.parcialbbb.ParticipanteRecyclerViewAdapter;
import com.ctx.parcialbbb.modelos.Participante;

import java.util.ArrayList;
import java.util.List;

public class ParticipanteContent {

    public static final List<Participante> ITEMS = new ArrayList<Participante>();
    public static ParticipanteRecyclerViewAdapter mAdapter;

    public static void clear() {
        ITEMS.clear();
    }

    public static void add(Participante item) {
        ITEMS.add(item);
    }

    public static void addAll(List<Participante> item) {
        ITEMS.addAll(item);
    }

    public static void setmAdapter(ParticipanteRecyclerViewAdapter mAdapter) {
        ParticipanteContent.mAdapter = mAdapter;
    }

    public static void notifyDataSetChanged() {
        if (ParticipanteContent.mAdapter != null) ParticipanteContent.mAdapter.notifyDataSetChanged();
    }
}
