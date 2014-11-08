package com.allesad.habraclient.fragments.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allesad.habraclient.R;
import com.allesad.habraclient.database.models.posts.Post;
import com.allesad.habraclient.events.PostsListEvent;
import com.allesad.habraclient.fragments.BaseSpicedFragment;
import com.allesad.habraclient.model.posts.PostCard;
import com.allesad.habraclient.robospice.listeners.PostsRequestListener;
import com.allesad.habraclient.robospice.requests.posts.PostsListRequest;
import com.allesad.habraclient.utils.Enums.PostsListType;
import com.allesad.habraclient.utils.Enums.RefreshType;
import com.allesad.habraclient.utils.Logger;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import uk.co.senab.actionbarpulltorefresh.library.viewdelegates.ViewDelegate;

/**
 * Created by Allesad on 23.03.2014.
 */
public abstract class BasePostsListFragment extends BaseSpicedFragment implements OnRefreshListener, AbsListView.OnScrollListener
{
    //=============================================================
    // Variables
    //=============================================================

    private ProgressBar mProgressBar;
    private PullToRefreshLayout mPullToRefreshLayout;
    private CardListView mList;

    private List<Card> mPosts;
    private CardArrayAdapter mAdapter;

    private boolean mIsLoading;
    private boolean mHasMorePosts = true;
    private RefreshType mRefreshType;

    //=============================================================
    // Abstract methods
    //=============================================================

    protected abstract PostsListType getType();
    protected abstract String getUrl();
    protected abstract int getPage();
    protected abstract void setPage(int page);

    //=============================================================
    // Fragment lifecycle
    //=============================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_refreshable_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressBar = (ProgressBar) view.findViewById(android.R.id.progress);
        mPullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.baseView_ptr_layout);
        mList = (CardListView) view.findViewById(R.id.baseView_list);
        mList.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true, this));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mIsLoading = false;

        ActionBarPullToRefresh.from(getActivity())
                .allChildrenArePullable()
                .listener(this)
                .useViewDelegate(TextView.class, new ViewDelegate() {
                    @Override
                    public boolean isReadyForPull(View view, float x, float y) {
                        return Boolean.TRUE;
                    }
                })
                .setup(mPullToRefreshLayout);

        setUpList();
        // TODO: add load posts from cache
        requestPosts(RefreshType.FROM_TOP);
    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);

        super.onPause();
    }

    //=============================================================
    // AbsListView.OnScrollListener
    //=============================================================

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {}

    @Override
    public void onScroll(AbsListView absListView, int firstVisible, int visibleCount, int totalCount) {
        if (totalCount == 0){
            return;
        }
        boolean loadMore = totalCount < firstVisible + visibleCount + 4;

        if (loadMore && !mIsLoading && mHasMorePosts){
            requestPosts(RefreshType.FROM_BOTTOM);
        }
    }

    //=============================================================
    // OnRefreshListener
    //=============================================================

    @Override
    public void onRefreshStarted(View view) {
        if (mIsLoading){
            mPullToRefreshLayout.setRefreshComplete();
        }else{
            requestPosts(RefreshType.FROM_TOP);
        }
    }

    //=============================================================
    // Event listeners
    //=============================================================

    public void onEvent(PostsListEvent event){
        Logger.v("Event type: " + event.getType() + ", current type: " + getType());
        if (event.getType() != getType()){
            return;
        }
        mProgressBar.setVisibility(View.GONE);
        mPullToRefreshLayout.setRefreshComplete();
        mIsLoading = false;
        List<Post> posts = event.getData();
        if (posts == null){
            return;
        }
        if (posts.size() < 10){
            mHasMorePosts = false;
        }

        List<PostCard> newPosts = new ArrayList<PostCard>();
        for (Post post : posts){
            PostCard card = new PostCard(getActivity(), post);

            // Replace post card if it is already exists in the list
            boolean alreadyExist = false;
            if (mRefreshType == RefreshType.FROM_BOTTOM){
                for (Card postCard : mPosts){
                    if (((PostCard) postCard).getPostId() == post.getPost_id()){
                        mPosts.set(mPosts.indexOf(postCard), card);
                        alreadyExist = true;
                    }
                }
            }
            if (!alreadyExist){
                newPosts.add(card);
            }
        }
        if (mRefreshType == RefreshType.FROM_TOP){
            mPosts.clear();
        }
        mPosts.addAll(newPosts);
        mAdapter.notifyDataSetChanged();

        if (mList.getEmptyView() == null && getView() != null){
            TextView emptyView = (TextView) getView().findViewById(android.R.id.empty);
            mList.setEmptyView(emptyView);
        }
    }

    //=============================================================
    // Private methods
    //=============================================================

    private void setUpList(){
        if (mPosts == null){
            mPosts = new ArrayList<Card>();
        }
        if (mAdapter == null){
            mAdapter = new CardArrayAdapter(getActivity(), mPosts);
        }
        mList.setAdapter(mAdapter);
    }

    private void requestPosts(RefreshType refreshType){
        if (!mIsLoading){
            switch (refreshType)
            {
                case FROM_TOP:
                    setPage(1);
                    mHasMorePosts = true;
                    break;
                case FROM_BOTTOM:
                    setPage(getPage() + 1);
                    break;
            }
            getSpiceManager().execute(new PostsListRequest(getUrl(), getPage()), new PostsRequestListener(getType()));
            mRefreshType = refreshType;
            mIsLoading = true;
        }
    }
}
