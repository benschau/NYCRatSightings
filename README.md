# NYCRatSightings
Semester-long project for CS2340.

## Contributing
#### Branches
This project has two permanent branches: `master` and `develop`. The master branch contains the most recent stable release. Never commit directly to master.
The develop branch serves as an integration branch for new features. Therefore, it may not always be stable. 
The project can also have temporary feature branches which should be kept local unless another developer wants to work on that branch.

#### Guidelines
If this is your first time contributing to the project, clone it:

`git clone https_or_ssh_link`

From the `develop` branch, create a new feature branch

`git checkout develop`

`git checkout -b new_feature_branch`

Commit your code changes to this new branch. Once you are done coding your feature, checkout the `develop` branch and `pull` to update it with any changes that may have been commited to it by other team members.

`git checkout develop`

`git pull`

If new commits were made to the develop branch while you were working on your feature branch, use the rebase tool to update your feature branch. If necessary, fix any merge conflicts.

`git checkout new_feature_name`

`git rebase develop`

Once this is done, merge your feature branch to `develop`, `push`, and delete your feature branch

`git checkout develop`

`git merge new_feature_branch`

`git push`

`git branch -d new_feature_branch`
