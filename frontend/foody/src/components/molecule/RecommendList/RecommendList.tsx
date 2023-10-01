import React, { WheelEvent, useRef, useState } from 'react';
import './RecommendList.scss';
import { Link } from 'react-router-dom';
import { FiRefreshCw } from 'react-icons/fi';
import { getRecommendList } from 'utils/api/recommend';
import RecommendListSkeleton from '../RecommendListSkeleton/RecommendListSkeleton';

interface RecommendListProps {
	title: string;
	index: string;
	list: [] | RecommendItem[];
}

function RecommendList({ title, index, list }: RecommendListProps) {
	const containerRef = useRef<HTMLUListElement | null>(null);
	const [isLoading, setIsLoading] = useState<boolean>(false);

	const handleRecommendRefresh = () => {
		setIsLoading(true);
		getRecommendList(index).then((res) => {
			if (res.data) {
				setIsLoading(false);
			}
		});
	};

	const handleScroll = (e: WheelEvent<HTMLUListElement>) => {
		const container = containerRef.current;
		if (container) {
			container.scrollLeft += e.deltaY;
		}
	};

	if (isLoading) {
		return <RecommendListSkeleton title={title} />;
	}

	return (
		<div className="recommend-list-container">
			<div className="recommend-list-header">
				<h2>{title}</h2>
				<button type="button" onClick={handleRecommendRefresh}>
					{index === 'hybrid' && <FiRefreshCw size={12} />}
				</button>
			</div>
			<div className="recommend-list-body">
				<ul className="recipe-list slider-container no-scrollbar" onWheel={handleScroll} ref={containerRef}>
					{index === 'ingredients' && list.length === 0 ? (
						<li className="blank-item-container">
							<span className="blank-desc">
								가지고 있는 재료를 3개 이상 등록하고
								<br />
								만들 수 있는 레시피를 추천 받아보세요!
							</span>
							<Link to="/refri">재료 등록하러 가기</Link>
						</li>
					) : (
						list &&
						list.map((item) => (
							<li key={item.id}>
								<Link to={`/recipe/${item.id}`}>
									<div className="info-wrap">
										<img src={item.url} alt="" />
										<div className="title">{item.name}</div>
									</div>
								</Link>
							</li>
						))
					)}
				</ul>
			</div>
		</div>
	);
}

export default RecommendList;
