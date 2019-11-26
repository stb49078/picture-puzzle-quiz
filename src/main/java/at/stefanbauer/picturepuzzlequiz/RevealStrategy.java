package at.stefanbauer.picturepuzzlequiz;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum RevealStrategy {
	RANDOM("Zufällig") {
		@Override
		public void showPiece(final List<Piece> pieces) {
			final List<Piece> collect = pieces.stream()
			                                  .filter(Piece::isPieceHidden)
			                                  .collect(Collectors.toList());
			Collections.shuffle(collect);
			collect.stream()
			       .findAny()
			       .ifPresent(Piece::show);
		}

		@Override
		public void hidePiece(final List<Piece> pieces) {
			final List<Piece> collect = pieces.stream()
			                                  .filter(piece -> !piece.isPieceHidden())
			                                  .collect(Collectors.toList());
			Collections.shuffle(collect);
			collect.stream()
			       .findAny()
			       .ifPresent(Piece::hide);
		}
	},
	OUTER_FIRST("von Außen nach Innen") {
		@Override
		public void showPiece(final List<Piece> pieces) {
			pieces.stream()
			      .filter(Piece::isPieceHidden)
			      .max(Comparator.comparing(Piece::getDistanceToCenter))
			      .ifPresent(Piece::show);
		}

		@Override
		public void hidePiece(final List<Piece> pieces) {
			pieces.stream()
			      .filter(piece -> !piece.isPieceHidden())
			      .max(Comparator.comparing(Piece::getDistanceToCenter))
			      .ifPresent(Piece::hide);
		}
	},
	INNER_FIRST("von Innen nach Außen") {
		@Override
		public void showPiece(final List<Piece> pieces) {
			pieces.stream()
			      .filter(Piece::isPieceHidden)
			      .min(Comparator.comparing(Piece::getDistanceToCenter))
			      .ifPresent(Piece::show);
		}

		@Override
		public void hidePiece(final List<Piece> pieces) {
			pieces.stream()
			      .filter(piece -> !piece.isPieceHidden())
			      .min(Comparator.comparing(Piece::getDistanceToCenter))
			      .ifPresent(Piece::hide);
		}
	}/*,
	ROW_COL("Reihe für Reihe") {
		@Override
		public void showPiece(final List<Piece> pieces) {

		}

		@Override
		public void hidePiece(final List<Piece> pieces) {

		}
	},
	COL_ROW("Spalte für Spalte") {
		@Override
		public void showPiece(final List<Piece> pieces) {

		}

		@Override
		public void hidePiece(final List<Piece> pieces) {

		}
	}*/;
	private final String caption;

	RevealStrategy(final String caption) {
		this.caption = caption;
	}

	public String getCaption() {
		return caption;
	}

	public abstract void showPiece(List<Piece> pieces);

	public abstract void hidePiece(List<Piece> pieces);
}
