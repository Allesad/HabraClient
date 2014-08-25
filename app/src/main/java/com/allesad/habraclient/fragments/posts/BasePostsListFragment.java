package com.allesad.habraclient.fragments.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allesad.habraclient.R;
import com.allesad.habraclient.fragments.BaseSpicedFragment;
import com.allesad.habraclient.model.posts.PostCard;
import com.allesad.habraclient.model.posts.PostListItemData;
import com.allesad.habraclient.robospice.requests.PostsListRequest;
import com.allesad.habraclient.robospice.response.posts.PostsListResponse;
import com.allesad.habraclient.utils.DialogUtil;
import com.allesad.habraclient.utils.Logger;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

/**
 * Created by Allesad on 23.03.2014.
 */
public abstract class BasePostsListFragment extends BaseSpicedFragment implements OnRefreshListener, AbsListView.OnScrollListener {

    private enum RefreshType {
        FROM_TOP, FROM_BOTTOM
    }

    protected abstract String getUrl();
    protected abstract int getPage();
    protected abstract void setPage(int page);

    private ProgressBar mProgressBar;
    private PullToRefreshLayout mPullToRefreshLayout;
    private CardListView mList;

    private List<Card> mPosts;
    private CardArrayAdapter mAdapter;

    private boolean mIsLoading;
    private boolean mHasMorePosts = true;
    private RefreshType mRefreshType;

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
                .setup(mPullToRefreshLayout);

        setUpList();
        requestPosts(RefreshType.FROM_TOP);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

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

    @Override
    public void onRefreshStarted(View view) {
        if (mIsLoading){
            mPullToRefreshLayout.setRefreshComplete();
        }else{
            requestPosts(RefreshType.FROM_TOP);
        }
    }

    private void setUpList(){
        if (mPosts == null){
            mPosts = new ArrayList<Card>();
        }
        if (mAdapter == null){
            mAdapter = new CardArrayAdapter(getActivity(), mPosts);
        }
        mList.setAdapter(mAdapter);
        //mList.setOnScrollListener(this);
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
            Logger.v("Request posts. Type: " + refreshType + ". Page: " + getPage());
            getSpiceManager().execute(new PostsListRequest(getUrl(), getPage()), new PostsListRequestListener());
            mRefreshType = refreshType;
            mIsLoading = true;
        }
    }

    private class PostsListRequestListener implements RequestListener<PostsListResponse> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            if (getActivity() != null){
                mProgressBar.setVisibility(View.GONE);
                mPullToRefreshLayout.setRefreshComplete();
                mIsLoading = false;
                DialogUtil.showAlertDialog(getActivity(), spiceException.getMessage());
            }
        }

        @Override
        public void onRequestSuccess(PostsListResponse response) {
            if (getActivity() == null || response == null){
                return;
            }

            mProgressBar.setVisibility(View.GONE);
            mPullToRefreshLayout.setRefreshComplete();
            mIsLoading = false;
            if (response.size() < 10){
                mHasMorePosts = false;
            }

            List<PostCard> newPosts = new ArrayList<PostCard>();
            for (PostListItemData post : response){
                PostCard card = new PostCard(getActivity(), post);

                // Replace post card if it is already exists in the list
                boolean alreadyExist = false;
                if (mRefreshType == RefreshType.FROM_BOTTOM){
                    for (Card postCard : mPosts){
                        if (((PostCard) postCard).getPostId() == post.getId()){
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
    }
}
