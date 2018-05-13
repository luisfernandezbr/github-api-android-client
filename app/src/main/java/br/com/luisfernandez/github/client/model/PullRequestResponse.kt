package br.com.luisfernandez.github.client.model

import com.google.gson.annotations.SerializedName

data class PullRequestResponse(
    @SerializedName("url") val url: String,
    @SerializedName("id") val id: Int,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("diff_url") val diffUrl: String,
    @SerializedName("patch_url") val patchUrl: String,
    @SerializedName("issue_url") val issueUrl: String,
    @SerializedName("number") val number: Int,
    @SerializedName("state") val state: String,
    @SerializedName("locked") val locked: Boolean,
    @SerializedName("title") val title: String,
    @SerializedName("user") val user: User,
    @SerializedName("body") val body: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("closed_at") val closedAt: Any?,
    @SerializedName("merged_at") val mergedAt: Any?,
    @SerializedName("merge_commit_sha") val mergeCommitSha: String,
    @SerializedName("requested_reviewers") val requestedReviewers: List<Any>,
    @SerializedName("requested_teams") val requestedTeams: List<Any>,
    @SerializedName("labels") val labels: List<Label>,
    @SerializedName("milestone") val milestone: Any?,
    @SerializedName("commits_url") val commitsUrl: String,
    @SerializedName("review_comments_url") val reviewCommentsUrl: String,
    @SerializedName("review_comment_url") val reviewCommentUrl: String,
    @SerializedName("comments_url") val commentsUrl: String,
    @SerializedName("statuses_url") val statusesUrl: String,
    @SerializedName("head") val head: Head,
    @SerializedName("author_association") val authorAssociation: String
)