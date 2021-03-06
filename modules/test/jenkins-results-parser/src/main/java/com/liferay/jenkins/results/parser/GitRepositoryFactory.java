/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Peter Yoo
 */
public class GitRepositoryFactory {

	public static WorkspaceGitRepository getDependencyWorkspaceGitRepository(
		String repositoryType, WorkspaceGitRepository workspaceGitRepository,
		PullRequest pullRequest, String upstreamBranchName) {

		if (repositoryType.equals("portal.companion")) {
			return new CompanionPortalWorkspaceGitRepository(
				pullRequest, upstreamBranchName, workspaceGitRepository);
		}
		else if (repositoryType.equals("portal.other")) {
			return new DefaultPortalWorkspaceGitRepository(
				pullRequest, upstreamBranchName);
		}
		else if (repositoryType.equals("plugins.portal")) {
			return new PluginsWorkspaceGitRepository(
				pullRequest, upstreamBranchName);
		}

		throw new RuntimeException(
			"Unsupported dependency workspace Git repository");
	}

	public static WorkspaceGitRepository getDependencyWorkspaceGitRepository(
		String repositoryType, WorkspaceGitRepository workspaceGitRepository,
		RemoteGitRef remoteGitRef, String upstreamBranchName) {

		if (repositoryType.equals("portal.companion")) {
			return new CompanionPortalWorkspaceGitRepository(
				remoteGitRef, upstreamBranchName, workspaceGitRepository);
		}
		else if (repositoryType.equals("portal.other")) {
			return new DefaultPortalWorkspaceGitRepository(
				remoteGitRef, upstreamBranchName);
		}
		else if (repositoryType.equals("portal.plugins")) {
			return new PluginsWorkspaceGitRepository(
				remoteGitRef, upstreamBranchName);
		}

		throw new RuntimeException(
			"Unsupported dependency workspace Git repository");
	}

	public static LocalGitRepository getLocalGitRepository(
		String repositoryName, String upstreamBranchName) {

		return new DefaultLocalGitRepository(
			repositoryName, upstreamBranchName);
	}

	public static RemoteGitRepository getRemoteGitRepository(
		GitRemote gitRemote) {

		String hostname = gitRemote.getHostname();

		if (hostname.equalsIgnoreCase("github.com")) {
			return new GitHubRemoteGitRepository(gitRemote);
		}

		return new DefaultRemoteGitRepository(gitRemote);
	}

	public static RemoteGitRepository getRemoteGitRepository(
		String hostname, String gitRepositoryName, String username) {

		if (hostname.equalsIgnoreCase("github.com")) {
			return new GitHubRemoteGitRepository(gitRepositoryName, username);
		}

		return new DefaultRemoteGitRepository(
			hostname, gitRepositoryName, username);
	}

	public static WorkspaceGitRepository getWorkspaceGitRepository(
		String gitHubURL, PullRequest pullRequest, String upstreamBranchName) {

		if (gitHubURL.contains("/liferay-plugins")) {
			return new PluginsWorkspaceGitRepository(
				pullRequest, upstreamBranchName);
		}
		else if (gitHubURL.contains("/liferay-portal")) {
			return new DefaultPortalWorkspaceGitRepository(
				pullRequest, upstreamBranchName);
		}

		return new DefaultWorkspaceGitRepository(
			pullRequest, upstreamBranchName);
	}

	public static WorkspaceGitRepository getWorkspaceGitRepository(
		String gitHubURL, RemoteGitRef remoteGitRef,
		String upstreamBranchName) {

		if (gitHubURL.contains("/liferay-plugins")) {
			return new PluginsWorkspaceGitRepository(
				remoteGitRef, upstreamBranchName);
		}
		else if (gitHubURL.contains("/liferay-portal")) {
			return new DefaultPortalWorkspaceGitRepository(
				remoteGitRef, upstreamBranchName);
		}

		return new DefaultWorkspaceGitRepository(
			remoteGitRef, upstreamBranchName);
	}

}