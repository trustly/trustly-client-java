export LC_ALL="C" # For some reason unset for me in IntelliJ terminal causing perl warnings
branch=`git rev-parse --abbrev-ref HEAD`
tag=`git log -1 --pretty=format:"%h"`
version=`git describe --abbrev=0 | perl -pe 's/\s*(\d+\.\d+\.)(\d+)\s*/$1 . ($2+1)/e'`

git log -1 --pretty=format:"%n%ci %Cred%h%Creset %s (%cr) %Cgreen<-- Last commit, branch%Creset %Cred$branch%Creset"
git log -3 --skip=1 --pretty=format:"%ci %h %s (%cr)"

echo "\n$version will be applied to $tag with:"
echo "#> git tag -as $version -m \"$@\" $tag"
echo "#> git push origin $version\n"
