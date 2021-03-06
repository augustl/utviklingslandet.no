.DEFAULT_GOAL := all
.PHONY: build
.PHONY: upload
.PHONY: invalidate
.PHONY: invalidate-cdn

invalidate:
	aws cloudfront create-invalidation --distribution-id E1HAXYWSPN0SDL --paths "/*"

invalidate-cdn:
	aws cloudfront create-invalidation --distribution-id E1S08RXGC67RG7 --paths "/*"

build:
	lein build-site

upload:
	aws s3 sync dist/ s3://utviklingslandet.no/ --delete

all: build upload