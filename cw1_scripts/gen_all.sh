echo 'generating shingle list'
time ./gen_shinglelist.sh

echo 'generating matrix'
time ./gen_matrix.sh

echo 'generating minhashes'
time ./gen_minhashes.sh

echo 'generating candidates'
time ./gen_candidates.sh
